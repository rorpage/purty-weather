package com.rorpage.purtyweather

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class PurtyWeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}