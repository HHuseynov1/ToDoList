package com.example.todolist.domain

import com.example.todolist.data.TaskDao
import com.example.todolist.data.Tasks

interface TaskRepository {
    suspend fun onChangeTask(tasks: Tasks)
    suspend fun deleteTask(tasks: Tasks)
    suspend fun insertTask(tasks: Tasks)
    suspend fun getItems(): List<Tasks>
    suspend fun getCompleted() : List<Tasks>
    suspend fun getActive() : List<Tasks>
}