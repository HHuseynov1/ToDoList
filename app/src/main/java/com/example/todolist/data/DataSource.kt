package com.example.todolist.data

interface LocalDataSource {
    suspend fun onChangeTask(tasks: Tasks)

    suspend fun deleteTask(tasks: Tasks)

    suspend fun insertTask(tasks: Tasks)

    suspend fun getLocalData(): List<Tasks>

    suspend fun getLocalDataCompleted() : List<Tasks>

    suspend fun getLocalDataActive() : List<Tasks>
}