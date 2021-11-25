package com.app.taybatApplication.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.media.RingtoneManager
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.app.taybatApplication.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.util.*


class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val id = 0

        val notification = remoteMessage.notification


        val manager = NotificationManagerCompat.from(applicationContext)

        createNotificationChannel()


        val notificationBuilder = NotificationCompat.Builder(applicationContext, getString(R.string.channel_ID))

        if (SDK_INT >= O) {
            notificationBuilder.setChannelId(getString(R.string.channel_ID))
        }

        notificationBuilder
                .setContentTitle(notification!!.getTitle())
                .setContentText(remoteMessage.notification!!.body)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(NotificationCompat.BigTextStyle())
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setVibrate(longArrayOf(1000, 500, 1000, 500, 1000, 500))
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)

        Objects.requireNonNull(manager).notify(id, notificationBuilder.build())

    }

    private fun createNotificationChannel() {
        if (SDK_INT < O) return

        val mgr = applicationContext.getSystemService<NotificationManager>(NotificationManager::class.java)
                ?: return

        val name = getString(R.string.channel_name)
        if (mgr.getNotificationChannel(name) == null) {
            val channel = NotificationChannel(getString(R.string.channel_ID), name, IMPORTANCE_HIGH)
            channel.description = getString(R.string.channel_description)
            channel.enableVibration(true)
            channel.enableLights(true)
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), channel.audioAttributes)
            channel.vibrationPattern = longArrayOf(1000, 500, 1000, 500, 1000, 500)
            channel.setBypassDnd(true)
            mgr.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        val TAG = "data"
        Log.d(TAG, "Refreshed token: $token")


    }


}
