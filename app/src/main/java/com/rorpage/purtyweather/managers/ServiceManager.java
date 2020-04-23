package com.rorpage.purtyweather.managers;

import android.content.Context;
import android.content.Intent;

import com.rorpage.purtyweather.services.UpdateWeatherService;

public class ServiceManager {
    public static void StartUpdateWeatherService(Context context) {
        Intent service = new Intent(context, UpdateWeatherService.class);
        context.startForegroundService(service);
    }
}
