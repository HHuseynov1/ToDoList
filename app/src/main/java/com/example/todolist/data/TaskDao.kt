package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    suspend fun insertTask(tasks: Tasks)

    @Update
    suspend fun onChangeTask(tasks: Tasks)

    @Delete
    suspend fun deleteTask(tasks: Tasks)

    @Query("SELECT * FROM TasksTable")
    suspend fun getTasksForAll() : List<Tasks>

    @Query("SELECT * FROM TasksTable where iSelected = 1")
    suspend fun getTasksForCompleted() : List<Tasks>

    @Query("SELECT * FROM TasksTable where iSelected = 0")
    suspend fun getTasksForActive() : List<Tasks>

//    @Query("SELECT * FROM TasksTable")
//    fun getTasksForActive() : Flow<List<Tasks>>
//
//    @Query("SELECT * FROM TasksTable")
//    fun getTasksForComplete() : Flow<List<Tasks>>
}