package com.rorpage.purtyweather.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rorpage.purtyweather.managers.ServiceManager
import com.rorpage.purtyweather.util.WeatherUpdateScheduler

class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        ServiceManager.startUpdateWeatherService(context)
        WeatherUpdateScheduler.scheduleJob(context)
    }
}