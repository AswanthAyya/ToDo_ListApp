package com.example.todolistapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todolistapp.UI_Screens.AddTodoScreen
import com.example.todolistapp.UI_Screens.EditTodoScreen
import com.example.todolistapp.UI_Screens.TodoEmptyScreen
import com.example.todolistapp.UI_Screens.TodoListScreen
import com.example.todolistapp.viewmodel.TodoViewModel

@Composable
fun TodoNavGraph(
    navController: NavHostController
) {
    val todoViewModel: TodoViewModel = viewModel()

    // ✅ COLLECT STATEFLOW
    val todos by todoViewModel.todos.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Routes.LIST
    ) {

        /* ---------- HOME (EMPTY / LIST) ---------- */
        composable(Routes.LIST) {

            if (todos.isEmpty()) {
                TodoEmptyScreen(
                    onAddClick = {
                        navController.navigate(Routes.ADD)
                    }
                )
            } else {
                TodoListScreen(
                    todos = todos,
                    onAddClick = { navController.navigate(Routes.ADD) },
                    onEditClick = {
                        todoViewModel.startEdit(it)
                        navController.navigate(Routes.EDIT)
                    },
                    onDeleteClick = { todoViewModel.deleteTodo(it) },
                    onToggleClick = { todoViewModel.toggleTodo(it) } // ✅ ADD THIS
                )

            }
        }

        /* ---------- ADD TODO ---------- */
        composable(Routes.ADD) {
            AddTodoScreen(
                onBackClick = { navController.popBackStack() },
                onAddTodo = {
                    todoViewModel.addTodo(it)
                    navController.popBackStack()
                }
            )
        }

        /* ---------- EDIT TODO ---------- */
        composable(Routes.EDIT) {
            EditTodoScreen(
                initialText = todoViewModel.getEditTodo(),
                onBackClick = { navController.popBackStack() },
                onUpdateTodo = { updatedTitle ->
                    val updatedTodo = todoViewModel
                        .getEditingTodo()
                        ?.copy(title = updatedTitle)

                    if (updatedTodo != null) {
                        todoViewModel.updateTodo(updatedTodo)
                    }

                    navController.popBackStack()
                }
            )
        }

    }
}
