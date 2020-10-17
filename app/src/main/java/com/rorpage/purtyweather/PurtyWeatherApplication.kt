package com.rorpage.purtyweather

import android.app.Application
import com.android.volley.VolleyLog
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class PurtyWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            VolleyLog.DEBUG = true
        }
    }
}