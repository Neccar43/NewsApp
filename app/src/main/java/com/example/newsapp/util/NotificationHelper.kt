package com.example.newsapp.util

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.newsapp.R
import kotlin.random.Random

class NotificationHelper(private val context: Context) {
    private val notificationManager: NotificationManagerCompat = NotificationManagerCompat.from(context)

    @SuppressLint("MissingPermission")
    fun showNotification(title: String, message: String) {
        val channelId = "news_channel"
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.baseline_person_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "News Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = Random.nextInt() // Rastgele bir notificationId oluşturun
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

}
