package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("TasksTable")
data class Tasks(
    @PrimaryKey(true)
    val id: Int,
    val iSelected: Boolean,
    val taskName: String
)
