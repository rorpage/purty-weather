package com.rorpage.purtyweather.services

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import timber.log.Timber

// TODO: 10/9/20 IntentService deprecated in API level 30, investigate WorkManager or JobIntentService
open class BaseService : IntentService("BaseService") {
    @Deprecated("Deprecated in Java")
    override fun onCreate() {
        super.onCreate()
        Timber.d("onCreate()")
    }

    @Deprecated("Deprecated in Java")
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Timber.d("onStartCommand()")
        return serviceStartCommandType()
    }

    @Deprecated("Deprecated in Java")
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        Timber.d("onHandleIntent")
    }

    protected fun serviceStartCommandType(): Int {
        return START_STICKY
    }
}
