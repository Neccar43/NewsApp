package com.example.newsapp.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.newsapp.MainActivity
import com.example.newsapp.R

class NotificationWorker(context: Context, workerParams: WorkerParameters) : Worker(
    context,
    workerParams
) {
    override fun doWork(): Result {

        sendNotification()

        return Result.success()
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun sendNotification() {
        val builder: NotificationCompat.Builder
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        )//bildirime tıklandığında gidilecek yer
        val intentToGo = PendingIntent.getActivities(
            applicationContext, 1,
            arrayOf(intent), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chanelID="randomId"
            val chanelName="Haberler"
            val chanelImportance=NotificationManager.IMPORTANCE_HIGH


            var channel:NotificationChannel?=notificationManager.getNotificationChannel(chanelID)
            if (channel==null){
                channel= NotificationChannel(chanelID,chanelName,chanelImportance)
                channel.description="Haber kanalı."
                notificationManager.createNotificationChannel(channel)
            }

            builder = NotificationCompat.Builder(applicationContext,chanelID)
            builder.setContentTitle("Gündemi kaçırma!")
                .setContentText("Gündemden haberdar olmak için uygulmayı açabilirsiniz.")
                .setSmallIcon(R.drawable.baseline_newspaper_24)
                .setContentIntent(intentToGo)
                .setAutoCancel(true)
        } else {
            builder = NotificationCompat.Builder(applicationContext)
            builder.setContentTitle("Gündemi kaçırma!")
                .setContentText("Gündemden haberdar olmak için uygulmayı açabilirsiniz.")
                .setSmallIcon(R.drawable.baseline_newspaper_24)
                .setContentIntent(intentToGo)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH
        }

        notificationManager.notify(1,builder.build())
    }
}