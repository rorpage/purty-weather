package com.rorpage.purtyweather.managers

import android.content.Context
import android.content.Intent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.services.UpdateWeatherService

@ExperimentalPagerApi
object ServiceManager {
    @JvmStatic
    fun startUpdateWeatherService(context: Context) {
        val service = Intent(context, UpdateWeatherService::class.java)
        context.startForegroundService(service)
    }
}