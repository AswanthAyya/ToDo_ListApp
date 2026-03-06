package com.example.todolistapp.data.repository

import com.example.todolistapp.data.local.TodoDao
import com.example.todolistapp.data.local.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val todoDao: TodoDao
) {

    fun getAllTodos(): Flow<List<TodoEntity>> {
        return todoDao.getAllTodos()
    }

    suspend fun addTodo(todo: TodoEntity) {
        todoDao.insertTodo(todo)
    }

    suspend fun updateTodo(todo: TodoEntity) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: TodoEntity) {
        todoDao.deleteTodo(todo)
    }
}


