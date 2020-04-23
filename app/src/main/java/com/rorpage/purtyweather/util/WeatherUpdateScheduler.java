package com.rorpage.purtyweather.util;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

import com.rorpage.purtyweather.services.PurtyWeatherService;

public class WeatherUpdateScheduler {
    public static void scheduleJob(Context context, int minimumLatencyMinutes) {
        ComponentName serviceComponent = new ComponentName(context, PurtyWeatherService.class);

        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(minimumLatencyMinutes * 60000);

        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }

    public static void scheduleJob(Context context) {
        scheduleJob(context, 15);
    }
}
