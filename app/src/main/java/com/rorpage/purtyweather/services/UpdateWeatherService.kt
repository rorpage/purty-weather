package com.rorpage.purtyweather.services

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rorpage.purtyweather.BuildConfig
import com.rorpage.purtyweather.database.daos.CurrentTemperatureDAO
import com.rorpage.purtyweather.database.entities.CurrentTemperature
import com.rorpage.purtyweather.managers.NotificationManager
import com.rorpage.purtyweather.models.WeatherResponse
import com.rorpage.purtyweather.util.GsonRequest
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class UpdateWeatherService : BaseService() {
    @Inject lateinit var currentTemperatureDAO: CurrentTemperatureDAO
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
                        getWeather(location.latitude, location.longitude)
                    }
                }
    }

    private fun getWeather(latitude: Double, longitude: Double) {
        Timber.d("getWeather")
        val queue = Volley.newRequestQueue(applicationContext)
        val url = String.format(
                "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=%s&units=%s",
                latitude.toString(),
                longitude.toString(),
                BuildConfig.OWMAPIKEY,
                "imperial")
        val weatherResponseGsonRequest = GsonRequest(
                url, WeatherResponse::class.java, null, { weatherResponse ->
            val currentTemperature = weatherResponse.current?.temp
            val title = String.format(Locale.US,
                    "%.0f\u00B0 and %s. Feels like %.0f\u00B0.",
                    currentTemperature,
                    weatherResponse.current!!.weather!![0].description,
                    weatherResponse.current!!.feels_like)
            val today = weatherResponse.daily!![0]
            val subtitle = String.format(Locale.US,
                    "Today: High %.0f\u00B0, low %.0f\u00B0, %s",
                    today.temp?.max,
                    today.temp?.min,
                    today.weather!![0].description)
            val iconId = getIconId(currentTemperature)
            mNotificationManager.sendNotification(title, subtitle, iconId)
            // TODO: 10/17/20 this is where we should save things to database for now, will change with refactor to retrofit
            currentTemperatureDAO.insertCurrentTemperature(
                    CurrentTemperature(1,
                            currentTemperature ?: -40.0,
                    weatherResponse.current?.feels_like ?: -40.0))
        }
        ) { error -> Timber.e(error) }
        queue.add(weatherResponseGsonRequest)
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