package com.example.todolist.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tasks::class], version = 1)
abstract class TasksDatabase : RoomDatabase() {

    abstract val dao : TaskDao



}