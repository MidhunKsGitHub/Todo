package com.midhun.todo.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.midhun.todo.model.TaskModel
import com.midhun.todo.notification.AlarmHelper
import com.midhun.todo.repository.TaskRepository
import com.midhun.todo.workManager.WorkManagerHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository,private val context: Context):ViewModel() {

    val tasks:StateFlow<List<TaskModel>> = repository.allTasks.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        emptyList()
    )

    fun getTaskById(taskId:Int): Flow<TaskModel> = repository.getTaskById(taskId)

    fun addTask(taskModel: TaskModel){
        viewModelScope.launch {
            repository.insert(taskModel)
        }

        AlarmHelper.scheduleTaskReminder(context,taskModel)
       //WorkManagerHelper.scheduleTaskReminder(context,taskModel)
    }

    fun updateTask(taskModel: TaskModel){
        viewModelScope.launch {
            repository.update(taskModel)
        }
        //WorkManagerHelper.scheduleTaskReminder(context,taskModel)
        AlarmHelper.scheduleTaskReminder(context,taskModel)
    }

    fun deleteTask(taskModel: TaskModel){
        viewModelScope.launch {
            repository.delete(taskModel)
        }
        WorkManager.getInstance(context).cancelUniqueWork("reminder_${taskModel.id}")
    }
}
