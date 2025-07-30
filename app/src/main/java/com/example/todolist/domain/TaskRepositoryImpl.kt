package com.example.todolist.domain

import com.example.todolist.data.LocalDataSource
import com.example.todolist.data.TaskDao
import com.example.todolist.data.Tasks
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource,
): TaskRepository {

    override suspend fun onChangeTask(tasks: Tasks) {
        val localChange = localDataSource.onChangeTask(tasks)
        return localChange
    }

    override suspend fun deleteTask(tasks: Tasks) {
        val localDelete = localDataSource.deleteTask(tasks)
        return localDelete
    }

    override suspend fun insertTask(tasks : Tasks) {
        val localTasks = localDataSource.insertTask(tasks)
        return localTasks
    }

    override suspend fun getItems(): List<Tasks> {
        val localItems = localDataSource.getLocalData()
        return localItems
    }

    override suspend fun getCompleted() : List<Tasks>{
        val localCompleted = localDataSource.getLocalDataCompleted()
        return localCompleted
    }

    override suspend fun getActive() : List<Tasks>{
        val localCompleted = localDataSource.getLocalDataActive()
        return localCompleted
    }

}