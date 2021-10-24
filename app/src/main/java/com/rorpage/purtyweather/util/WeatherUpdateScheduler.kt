package com.rorpage.purtyweather.util

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.services.BackgroundWork
import java.util.concurrent.TimeUnit

object WeatherUpdateScheduler {
    @ExperimentalPagerApi
    @JvmStatic
    @JvmOverloads
    fun scheduleJob(context: Context, fromUI: Boolean) {

        val minimumLatencyMinutes = 15

        val workRequest = if (fromUI) {
            OneTimeWorkRequestBuilder<BackgroundWork>()
                .addTag("weatherApiCall")
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()
        } else {
            PeriodicWorkRequestBuilder<BackgroundWork>(minimumLatencyMinutes.toLong(), TimeUnit.MINUTES)
                .addTag("weatherApiCall")
                .build()
        }

        val wm = WorkManager.getInstance(context)
//        val future: ListenableFuture<List<WorkInfo>> = wm.getWorkInfosByTag("weatherApiCall")
//        val list: List<WorkInfo> = future.get()
//        // start only if no such tasks present
//        if (list.size == 0 || list.get(0).progr) {
//            // shedule the task
            wm.enqueue(workRequest)
//        } else {
//            // this periodic task has been previously scheduled
//        }

    }
}