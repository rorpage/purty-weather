package com.rorpage.purtyweather.managers

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rorpage.purtyweather.MainActivity
import com.rorpage.purtyweather.R

@ExperimentalPagerApi
class NotificationManager(private val mContext: Context) {
    private val mNotificationManager: NotificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun sendNotification(message: String?): Notification {
        val smallIcon = mContext.resources
                .getIdentifier("ic_launcher", "mipmap",
                        mContext.packageName)
        val notificationTitle = mContext.getString(R.string.app_name)
        return sendNotification(notificationTitle, message, smallIcon)
    }

    fun sendNotification(title: String, message: String?, smallIcon: Int): Notification {
        buildNotificationChannelIfNeeded()
        val pendingIntent = pendingIntent
        val notificationBuilder = getNotificationBuilder(title, message,
                pendingIntent, smallIcon)
        val notification = notificationBuilder.build()
        mNotificationManager.notify(NOTIFICATION_ID, notification)
        return notification
    }

    private fun getNotificationBuilder(title: String, message: String?,
                                       pendingIntent: PendingIntent,
                                       smallIcon: Int): NotificationCompat.Builder {
        return NotificationCompat.Builder(mContext, PRIMARY_CHANNEL)
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(message)
                .setOngoing(true)
                .setVibrate(null)
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent)
    }

    private val pendingIntent: PendingIntent
        private get() {
            val intent = Intent(mContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return PendingIntent.getActivity(mContext, 0, intent, FLAG_IMMUTABLE)
        }

    private fun buildNotificationChannelIfNeeded() {
        val channel = NotificationChannel(PRIMARY_CHANNEL,
                mContext.getString(R.string.notification_channel_default),
                NotificationManager.IMPORTANCE_LOW)
        channel.setShowBadge(false)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        mNotificationManager.createNotificationChannel(channel)
    }

    companion object {
        const val NOTIFICATION_ID = 1
        private const val PRIMARY_CHANNEL = "default"
    }

}