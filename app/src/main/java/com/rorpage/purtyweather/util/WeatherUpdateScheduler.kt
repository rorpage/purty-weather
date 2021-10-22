package com.rorpage.purtyweather.util

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.services.BackgroundWork
import java.util.concurrent.TimeUnit

object WeatherUpdateScheduler {
    @ExperimentalPagerApi
    @JvmStatic
    @JvmOverloads
    fun scheduleJob(context: Context, minimumLatencyMinutes: Int = 1) {
        val backgroundWorkRequest = PeriodicWorkRequestBuilder<BackgroundWork>(minimumLatencyMinutes.toLong(), TimeUnit.MINUTES)
                // Additional configuration
                .build()

        WorkManager
            .getInstance(context)
            .enqueue(backgroundWorkRequest)
    }
}