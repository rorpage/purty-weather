package com.rorpage.purtyweather.util

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import com.rorpage.purtyweather.services.PurtyWeatherService

object WeatherUpdateScheduler {
    @JvmStatic
    @JvmOverloads
    fun scheduleJob(context: Context, minimumLatencyMinutes: Int = 15) {
        val serviceComponent = ComponentName(context, PurtyWeatherService::class.java)
        val builder = JobInfo.Builder(0, serviceComponent)
        builder.setMinimumLatency(minimumLatencyMinutes * 60000.toLong())
        val jobScheduler = context.getSystemService(JobScheduler::class.java)
        jobScheduler.schedule(builder.build())
    }
}