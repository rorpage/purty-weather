package com.rorpage.purtyweather.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.util.WeatherUpdateScheduler

@ExperimentalPagerApi
class BootCompletedReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //TODO really we want to use the previous work request.
        WeatherUpdateScheduler.scheduleJob(context, false)
    }
}