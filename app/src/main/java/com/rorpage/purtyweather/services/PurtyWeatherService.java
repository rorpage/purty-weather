package com.rorpage.purtyweather.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.rorpage.purtyweather.managers.ServiceManager;
import com.rorpage.purtyweather.util.WeatherUpdateScheduler;

import timber.log.Timber;

public class PurtyWeatherService extends JobService {

    @Override
    public boolean onStartJob(JobParameters params) {
        Timber.d("onStartJob");

        ServiceManager.startUpdateWeatherService(getApplicationContext());
        WeatherUpdateScheduler.scheduleJob(getApplicationContext());

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Timber.d("onStopJob");

        return true;
    }
}
