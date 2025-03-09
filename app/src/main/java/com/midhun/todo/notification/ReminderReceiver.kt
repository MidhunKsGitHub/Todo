package com.midhun.todo.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.midhun.todo.R


class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("taskTitle") ?: "Task Reminder"
        NotificationHelper(context).showNotification(title)
    }
}
