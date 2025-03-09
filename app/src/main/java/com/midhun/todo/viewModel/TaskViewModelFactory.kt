package com.midhun.todo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.midhun.todo.repository.TaskRepository

@Suppress("UNCHECKED_CAST")
class TaskViewModelFactory(private val repository: TaskRepository,private val context: Context) :ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository,context) as T
    }
    }
