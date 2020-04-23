package com.rorpage.purtyweather.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rorpage.purtyweather.util.WeatherUpdateScheduler;

public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        WeatherUpdateScheduler.scheduleJob(context);
    }
}
