package com.rorpage.purtyweather.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import timber.log.Timber;

public class BaseService extends IntentService {
    public BaseService() {
        super("BaseService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Timber.d("onStartCommand()");
        return ServiceStartCommandType();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Timber.d("onHandleIntent");
    }

    protected int ServiceStartCommandType() {
        return START_STICKY;
    }
}
