package com.rorpage.purtyweather.services

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class UpdateWeatherService : BaseService() {
    @Inject lateinit var currentWeatherDAO: CurrentWeatherDAO
    @Inject lateinit var weatherDAO: WeatherDAO
    @Inject lateinit var hourlyDAO: HourlyDAO
    @Inject lateinit var hourlyWeatherDAO: HourlyWeatherDAO
    @Inject lateinit var apiService: ApiService
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mNotificationManager: NotificationManager
    override fun onCreate() {
        super.onCreate()
        mNotificationManager = NotificationManager(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onHandleIntent(intent: Intent?) {
        super.onHandleIntent(intent)
        startForeground(NotificationManager.NOTIFICATION_ID,
                mNotificationManager.sendNotification("Loading..."))
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Timber.e("Location permission not granted")
            return
        }
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val result = getWeather(location.latitude, location.longitude)
                            when (result) {
                                is Result.Success -> handleWeatherResult(result.data)
                                is Result.Error -> Timber.e(result.exception.message)
                            }
                        }
                    }
                }
    }

    private fun handleWeatherResult(weatherResponse: WeatherResponse) {
        val currentTemperature = weatherResponse.current?.temp
        val title = String.format(Locale.US,
                "%.0f\u00B0 and %s. Feels like %.0f\u00B0.",
                currentTemperature,
                weatherResponse.current!!.weather!![0].description,
                weatherResponse.current!!.feelsLike)
        val today = weatherResponse.daily!![0]
        val subtitle = String.format(Locale.US,
                "Today: High %.0f\u00B0, low %.0f\u00B0, %s",
                today.temp?.max,
                today.temp?.min,
                today.weather!![0].description)
        val iconId = getIconId(currentTemperature)
        mNotificationManager.sendNotification(title, subtitle, iconId)

        val weatherEntityList = ArrayList<WeatherEntity>()
        val weatherEntityCounter = 0
        weatherResponse.current?.weather?.forEach {
            val weatherEntity = WeatherEntity(weatherEntityCounter.inc(), 1, it.id, it.main ?: "", it.description ?: "", it.icon ?: "01d")
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
                ))

        val hourlyId: Int = 0
        weatherResponse.hourly?.forEach {
            val hourlyEntity = HourlyEntity(hourlyId.inc(), it.temp,
                    it.feelsLike, it.dt, it.sunrise, it.sunset, it.pressure,
                    it.humidity, it.dewPoint, it.uvi, it.clouds, it.visibility,
                    it.windSpeed, it.windDeg)

            val hourlyWeatherEntityList = ArrayList<HourlyWeatherEntity>()
            val hourlyWeatherEntityCounter = 0
            it.weather?.forEach {
                hourlyWeatherEntityList.add(HourlyWeatherEntity(hourlyWeatherEntityCounter.inc(),
                        hourlyId, it.id, it.main ?: "",
                        it.description ?: "", it.icon ?: "01d"))
            }

            hourlyDAO.insertHourly(hourlyEntity)
            hourlyWeatherDAO.insertHourlyWeatherList(hourlyWeatherEntityList)
        }
    }

    private suspend fun getWeather(latitude: Double, longitude: Double) = safeApiCall(
            call = { getWeatherCall(latitude, longitude) },
            errorMessage = "Error occurred when trying to get weather."
    )

    private suspend fun getWeatherCall(latitude: Double, longitude: Double): Result<WeatherResponse> {
        Timber.d("getWeather")

        val response = apiService.getWeather(latitude, longitude)
        if (response.isSuccessful) {
            return Result.Success(response.body()!!)
        }

        val apiError: ApiError = ErrorUtils(applicationContext).parseError(response)
        return Result.Error(IOException(apiError.message))

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
        return resources.getIdentifier(name, defType, packageName)
    }
}