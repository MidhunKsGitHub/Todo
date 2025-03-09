package com.midhun.todo

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

class ToDoApp :Application() {

    override fun onCreate() {
        super.onCreate()
        WorkManager.initialize(this,
            Configuration.Builder().build())
    }
}