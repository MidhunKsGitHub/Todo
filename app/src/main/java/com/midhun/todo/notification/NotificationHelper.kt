package com.midhun.todo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.midhun.todo.R
import com.midhun.todo.screens.constants.Constants.CHANNEL_ID
import com.midhun.todo.screens.constants.Constants.CHANNEL_NAME

class NotificationHelper(private val context: Context
) {

     fun showNotification(taskTitle: String) {

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val channelId = CHANNEL_ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(taskTitle)
            .setContentText("You have a task pending !")
            .setSmallIcon(R.drawable.ic_time)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskTitle.hashCode(), notification)
    }
}