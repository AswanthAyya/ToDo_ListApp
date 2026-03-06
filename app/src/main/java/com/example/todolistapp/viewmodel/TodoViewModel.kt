package com.example.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.local.TodoDatabase
import com.example.todolistapp.data.local.TodoEntity
import com.example.todolistapp.data.repository.TodoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoViewModel(
    application: Application
) : AndroidViewModel(application) {

    /* ---------- DATABASE & REPO ---------- */

    private val dao = TodoDatabase
        .getDatabase(application)
        .todoDao()

    private val repository = TodoRepository(dao)

    private val _snackbarEvent = MutableSharedFlow<String>()
    val snackbarEvent = _snackbarEvent.asSharedFlow()

    /* ---------- TODOS (ROOM → UI) ---------- */

    val todos = repository.getAllTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /* ---------- EDIT STATE ---------- */

    private var editingTodo: TodoEntity? = null

    fun startEdit(todo: TodoEntity) {
        editingTodo = todo
    }

    fun getEditTodo(): String {
        return editingTodo?.title ?: ""
    }

    /* ---------- ACTIONS ---------- */

    fun addTodo(title: String) {
        viewModelScope.launch {
            repository.addTodo(
                TodoEntity(title = title)
            )
            _snackbarEvent.emit("Task added successfully")
        }
    }

    fun updateTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(todo)
            _snackbarEvent.emit("Task updated successfully")
        }
    }

    fun deleteTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
            _snackbarEvent.emit("Task deleted")
        }
    }

    fun toggleTodo(todo: TodoEntity) {
        viewModelScope.launch {
            repository.updateTodo(
                todo.copy(isCompleted = !todo.isCompleted)
            )
        }
    }

    fun getEditingTodo(): TodoEntity? = editingTodo


}
