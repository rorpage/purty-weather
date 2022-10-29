package com.rorpage.purtyweather.util

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.services.BackgroundWork
import java.util.concurrent.TimeUnit

object WeatherUpdateScheduler {

    val tagName = "weatherApiCall"

    @ExperimentalPagerApi
    @JvmStatic
    @JvmOverloads
    fun scheduleJob(context: Context, fromUI: Boolean) {
        val minimumLatencyMinutes = 15L

        val wm = WorkManager.getInstance(context)

        val periodicWorkRequest = PeriodicWorkRequestBuilder<BackgroundWork>(minimumLatencyMinutes, TimeUnit.MINUTES)
            .addTag(tagName)

        if (fromUI) {
            val workRequest = OneTimeWorkRequestBuilder<BackgroundWork>()
                .addTag(tagName)
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
            wm.enqueue(workRequest)

            periodicWorkRequest.setInitialDelay(minimumLatencyMinutes, TimeUnit.MINUTES)
        }

        // even if we're from the UI, make sure this is scheduled.
        wm.enqueueUniquePeriodicWork(tagName, ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest.build())
    }
}
