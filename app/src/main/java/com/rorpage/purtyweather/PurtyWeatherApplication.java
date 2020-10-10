package com.rorpage.purtyweather;

import android.app.Application;

import com.android.volley.VolleyLog;

import timber.log.Timber;

public class PurtyWeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            VolleyLog.DEBUG = true;
        }
    }
}
