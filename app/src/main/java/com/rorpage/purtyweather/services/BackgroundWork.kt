package com.rorpage.purtyweather.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rorpage.purtyweather.R
import com.rorpage.purtyweather.database.daos.CurrentWeatherDAO
import com.rorpage.purtyweather.database.daos.HourlyDAO
import com.rorpage.purtyweather.database.daos.HourlyWeatherDAO
import com.rorpage.purtyweather.database.daos.WeatherDAO
import com.rorpage.purtyweather.database.entities.CurrentWeather
import com.rorpage.purtyweather.database.entities.HourlyEntity
import com.rorpage.purtyweather.database.entities.HourlyWeatherEntity
import com.rorpage.purtyweather.database.entities.WeatherEntity
import com.rorpage.purtyweather.managers.NotificationManager
import com.rorpage.purtyweather.models.ApiError
import com.rorpage.purtyweather.models.WeatherResponse
import com.rorpage.purtyweather.network.ApiService
import com.rorpage.purtyweather.network.ErrorUtils
import com.rorpage.purtyweather.network.Result
import com.rorpage.purtyweather.network.safeApiCall
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt

@ExperimentalPagerApi
@HiltWorker
class BackgroundWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    val currentWeatherDAO: CurrentWeatherDAO,
    val weatherDAO: WeatherDAO,
    val hourlyDAO: HourlyDAO,
    val hourlyWeatherDAO: HourlyWeatherDAO,
    val apiService: ApiService,
) : CoroutineWorker(appContext, workerParams) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mNotificationManager: NotificationManager

    override suspend fun doWork(): Result {

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Timber.e("Location permission not granted")
            return Result.failure()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
        mNotificationManager = NotificationManager(applicationContext)
        //TODO version of
        // startForeground(NotificationManager.NOTIFICATION_ID,
        //            mNotificationManager.sendNotification("Loading..."))

        val location = fusedLocationClient.lastLocation.await()

        Timber.d("location is $location")

        var returnResult = Result.failure()

        if (location != null) {
            //TODO block on this. right now it's not so the result is always failure
            CoroutineScope(Dispatchers.IO).launch {
                val result = getWeather(location.latitude, location.longitude)
                returnResult = when (result) {
                    is com.rorpage.purtyweather.network.Result.Success -> {
                        handleWeatherResult(result.data)
                        Result.success()
                    }
                    is com.rorpage.purtyweather.network.Result.Error -> {
                        Timber.e(result.exception)
                        Result.failure()
                    }
                }
            }
        }

        return returnResult
    }

    private fun handleWeatherResult(weatherResponse: WeatherResponse) {
        val currentTemperature = weatherResponse.current?.temp
        val title = String.format(
            Locale.US,
            "%.0f\u00B0 and %s. Feels like %.0f\u00B0.",
            currentTemperature,
            weatherResponse.current!!.weather!![0].description,
            weatherResponse.current!!.feelsLike)
        val today = weatherResponse.daily!![0]
        val subtitle = String.format(
            Locale.US,
            "Today: High %.0f\u00B0, low %.0f\u00B0, %s",
            today.temp?.max,
            today.temp?.min,
            today.weather!![0].description)
        val iconId = getIconId(currentTemperature)
        mNotificationManager.sendNotification(title, subtitle, iconId)

        purgeDB()

        val weatherEntityList = ArrayList<WeatherEntity>()
        var weatherEntityCounter = 0
        weatherResponse.current?.weather?.forEach {
            val weatherEntity = WeatherEntity(weatherEntityCounter++, 1, it.id, it.main ?: "", it.description ?: "", it.icon ?: "01d")
            weatherEntityList.add(weatherEntity)
        }

        weatherDAO.insertWeatherList(weatherEntityList)

        currentWeatherDAO.insertCurrentTemperature(
            CurrentWeather(1,
                currentTemperature ?: -40.0,
                weatherResponse.current?.feelsLike ?: -40.0,
                weatherResponse.current?.dt ?: 0,
                weatherResponse.current?.sunrise ?: 0,
                weatherResponse.current?.sunset ?: 0,
                weatherResponse.current?.pressure ?: 0,
                weatherResponse.current?.humidity ?: 0,
                weatherResponse.current?.dewPoint ?: 0.0,
                weatherResponse.current?.uvi ?: 0.0,
                weatherResponse.current?.clouds ?: 0,
                weatherResponse.current?.visibility ?: 0,
                weatherResponse.current?.windSpeed ?: 0.0,
                weatherResponse.current?.windDeg ?: 0
            )
        )

        var hourlyId = 1
        var hourlyWeatherEntityCounter = 1
        weatherResponse.hourly?.forEach { weatherInfoUnit ->
            Timber.v("Saving hourly entity with id: ${hourlyId + 1}")
            val hourlyEntity = HourlyEntity(hourlyId++, weatherInfoUnit.temp,
                weatherInfoUnit.feelsLike, weatherInfoUnit.dt, weatherInfoUnit.sunrise, weatherInfoUnit.sunset, weatherInfoUnit.pressure,
                weatherInfoUnit.humidity, weatherInfoUnit.dewPoint, weatherInfoUnit.uvi, weatherInfoUnit.clouds, weatherInfoUnit.visibility,
                weatherInfoUnit.windSpeed, weatherInfoUnit.windDeg)

            val hourlyWeatherEntityList = ArrayList<HourlyWeatherEntity>()
            weatherInfoUnit.weather?.forEach {
                Timber.v("Saving hourly weather entity with id: ${hourlyWeatherEntityCounter + 1} and hourly id: $hourlyId")
                hourlyWeatherEntityList.add(
                    HourlyWeatherEntity(hourlyWeatherEntityCounter++,
                    hourlyId - 1, it.id, it.main ?: "",
                    it.description ?: "", it.icon ?: "01d")
                )
            }

            hourlyDAO.insertHourly(hourlyEntity)
            hourlyWeatherDAO.insertHourlyWeatherList(hourlyWeatherEntityList)
        }
    }

    private suspend fun getWeather(latitude: Double, longitude: Double) = safeApiCall(
        call = { getWeatherCall(latitude, longitude) },
        errorMessage = "Error occurred when trying to get weather."
    )

    private suspend fun getWeatherCall(latitude: Double, longitude: Double): com.rorpage.purtyweather.network.Result<WeatherResponse> {
        Timber.d("getWeather")

        val response = apiService.getWeather(latitude, longitude)
        if (response.isSuccessful) {
            return com.rorpage.purtyweather.network.Result.Success(response.body()!!)
        }

        val apiError: ApiError = ErrorUtils(applicationContext).parseError(response)
        return com.rorpage.purtyweather.network.Result.Error(IOException(apiError.message))
    }

    private fun getIconId(temperature: Double?): Int {
        return if (temperature != null) {
            try {
                val iconFormat = if (temperature < 0) "tempn%d" else "temp%d"
                val iconName = String.format(iconFormat, temperature.roundToInt())
                getIconIdFromResources(iconName, "drawable")
            } catch (e: Exception) {
                Timber.e(e)
                getIconIdFromResources("ic_stat_wb_sunny", "mipmap")
            }
        } else {
            getIconIdFromResources("ic_stat_wb_sunny", "mipmap")
        }
    }

    private fun getIconIdFromResources(name: String, defType: String): Int {
        return applicationContext.resources.getIdentifier(name, defType, applicationContext.packageName)
    }

    private fun purgeDB() {
        currentWeatherDAO.deleteAll()
        hourlyDAO.deleteAll()
        hourlyWeatherDAO.deleteAll()
        weatherDAO.deleteAll()
    }

}