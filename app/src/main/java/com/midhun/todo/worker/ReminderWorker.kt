package com.midhun.todo.worker



import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.midhun.todo.notification.NotificationHelper


class ReminderWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val taskTitle = inputData.getString("taskTitle") ?: "Task Reminder"
        NotificationHelper(context = applicationContext).showNotification(taskTitle)
        return Result.success()
    }


}