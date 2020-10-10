package com.rorpage.purtyweather.services

import android.app.job.JobParameters
import android.app.job.JobService
import com.rorpage.purtyweather.managers.ServiceManager
import com.rorpage.purtyweather.util.WeatherUpdateScheduler
import timber.log.Timber

class PurtyWeatherService : JobService() {
    override fun onStartJob(params: JobParameters): Boolean {
        Timber.d("onStartJob")
        ServiceManager.startUpdateWeatherService(applicationContext)
        WeatherUpdateScheduler.scheduleJob(applicationContext)
        return true
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Timber.d("onStopJob")
        return true
    }
}