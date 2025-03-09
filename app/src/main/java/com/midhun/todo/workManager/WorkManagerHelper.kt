package com.midhun.todo.workManager

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.midhun.todo.model.TaskModel
import com.midhun.todo.worker.ReminderWorker
import java.util.concurrent.TimeUnit

object WorkManagerHelper {

    fun scheduleTaskReminder(context: Context, task: TaskModel) {
        val workManager = WorkManager.getInstance(context)

        val data = Data.Builder()
            .putString("taskTitle", task.title)
            .build()

        val delay = maxOf( task.dueDate - System.currentTimeMillis(),0)
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .build()

        workManager.enqueueUniqueWork(
            "reminder_${task.id}",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }
}