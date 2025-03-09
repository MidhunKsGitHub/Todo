package com.midhun.todo.repository

import com.midhun.todo.dao.TaskDao
import com.midhun.todo.model.TaskModel
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks:Flow<List<TaskModel>> = taskDao.getAllTasks()

    fun getTaskById(taskId:Int):Flow<TaskModel> = taskDao.getTasksById(taskId)

    suspend fun insert(taskModel: TaskModel){
        taskDao.insertTask(taskModel)
    }

    suspend fun update(taskModel: TaskModel){
        taskDao.updateTask(taskModel)
    }
    suspend fun delete(taskModel: TaskModel){
        taskDao.deleteTask(taskModel)
    }
}