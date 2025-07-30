package com.example.todolist.presentation.ui.viewmodel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.TaskDao
import com.example.todolist.data.Tasks
import com.example.todolist.domain.TaskRepository
import com.example.todolist.presentation.ui.event.HomeUiEvents
import com.example.todolist.presentation.ui.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: TaskDao,
    private val repo: TaskRepository
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        refreshAllTasks()
    }

    fun homeUiEvent(event: HomeUiEvents) {
        when (event) {

            is HomeUiEvents.state -> {
                _homeUiState.update {
                    it.copy(state = event.state)
                }
            }

            is HomeUiEvents.addTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.e("task", event.task.toString())
                    repo.insertTask(event.task.copy(id = 0))
                    refreshAllTasks()
                }
            }

            is HomeUiEvents.filterChange -> {
                _homeUiState.update {
                    it.copy(filterChange = event.filterType)
                }
            }

            is HomeUiEvents.getCompleted -> {
                viewModelScope.launch(Dispatchers.IO) {
                    val completed = repo.getCompleted()
                    _homeUiState.update {
                        it.copy(completed = completed)
                    }
                }
            }

            is HomeUiEvents.update -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dao.onChangeTask(event.task)
                    refreshAllTasks()
                }
            }

            is HomeUiEvents.deleteItem -> deleteTask(event.tasks)
        }
    }

    fun deleteTask(task: Tasks) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteTask(task)
            refreshAllTasks()
        }
    }

    private fun refreshAllTasks() {

        viewModelScope.launch(Dispatchers.IO) {
            val all = dao.getTasksForAll()
            val active = dao.getTasksForActive()
            val completed = dao.getTasksForCompleted()

            withContext(Dispatchers.Main){
                _homeUiState.update {
                    it.copy(
                        all = all,
                        active = active,
                        completed = completed
                    )
                }
            }
        }
    }
}