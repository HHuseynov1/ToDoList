package com.example.todolist.presentation.ui.home

import com.example.todolist.MainActivity
import com.example.todolist.data.Tasks

data class HomeUiState(
    val all: List<Tasks> = listOf(),
    val state: Boolean = false,
    val active: List<Tasks> = listOf(),
    val completed: List<Tasks> = listOf(),
    val filterChange: MainActivity.FilterType = MainActivity.FilterType.ALL
)