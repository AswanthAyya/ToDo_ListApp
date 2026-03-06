package com.example.todolistapp.ui.util

fun isValidTodo(text: String): Boolean {
    return text.trim().split("\\s+".toRegex()).size >= 3
}
