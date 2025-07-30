package com.example.todolist.data

interface LocalDataSource {

    suspend fun insertTask(tasks: Tasks)

    suspend fun getLocalData(): List<Tasks>

    suspend fun getLocalDataCompleted() : List<Tasks>

    suspend fun getLocalDataActive() : List<Tasks>
}