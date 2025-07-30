package com.example.todolist.domain

import com.example.todolist.data.LocalDataSource
import com.example.todolist.data.TaskDao
import com.example.todolist.data.Tasks
import jakarta.inject.Inject

class RoomLocalDataSource @Inject constructor(
    private val dao: TaskDao
) : LocalDataSource {

    override suspend fun onChangeTask(tasks: Tasks) = dao.onChangeTask(tasks)

    override suspend fun deleteTask(tasks: Tasks) = dao.deleteTask(tasks)

    override suspend fun insertTask(tasks: Tasks) = dao.insertTask(tasks)

    override suspend fun getLocalData() = dao.getTasksForAll()

    override suspend fun getLocalDataCompleted() = dao.getTasksForCompleted()

    override suspend fun getLocalDataActive() = dao.getTasksForActive()

}