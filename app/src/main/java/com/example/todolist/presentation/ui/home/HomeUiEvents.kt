package com.example.todolist.presentation.ui.home

import com.example.todolist.MainActivity
import com.example.todolist.data.Tasks

sealed interface HomeUiEvents {

    data class addTask(val task: Tasks) : HomeUiEvents

    data class update(val task: Tasks) : HomeUiEvents

    data class state(val state: Boolean) : HomeUiEvents

    data class filterChange(val filterType: MainActivity.FilterType) : HomeUiEvents

    data class deleteItem(val tasks: Tasks) : HomeUiEvents

    object getCompleted : HomeUiEvents

}