package com.rorpage.purtyweather.managers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.rorpage.purtyweather.MainActivity;
import com.rorpage.purtyweather.R;

public class NotificationManager {
    public static final int NOTIFICATION_ID = 1;
    private static final String PRIMARY_CHANNEL = "default";

    private Context mContext;
    private android.app.NotificationManager mNotificationManager;

    public NotificationManager(Context context) {
        mContext = context;
        mNotificationManager =
                (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public Notification sendNotification(String message) {
        final int smallIcon = mContext.getResources()
                .getIdentifier("ic_launcher", "mipmap",
                        mContext.getPackageName());

        final String notificationTitle = mContext.getString(R.string.app_name);
        return sendNotification(notificationTitle, message, smallIcon);
    }

    public Notification sendNotification(String title, String message, int smallIcon) {
        buildNotificationChannelIfNeeded();

        PendingIntent pendingIntent = getPendingIntent();

        NotificationCompat.Builder notificationBuilder = getNotificationBuilder(title, message,
                pendingIntent, smallIcon);

        Notification notification = notificationBuilder.build();

        mNotificationManager.notify(NOTIFICATION_ID, notification);

        return notification;
    }

    private NotificationCompat.Builder getNotificationBuilder(String title, String message,
                                                              PendingIntent pendingIntent,
                                                              int smallIcon) {
        return new NotificationCompat.Builder(mContext, PRIMARY_CHANNEL)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(message)
                .setOngoing(true)
                .setVibrate(null)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
    }

    private void buildNotificationChannelIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL,
                    mContext.getString(R.string.notification_channel_default),
                    android.app.NotificationManager.IMPORTANCE_LOW);

            channel.setShowBadge(false);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            mNotificationManager.createNotificationChannel(channel);
        }
    }
}
