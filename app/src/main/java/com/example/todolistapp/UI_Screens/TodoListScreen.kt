package com.example.todolistapp.UI_Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolistapp.data.local.TodoEntity
import com.example.todolistapp.ui.theme.LightBg
import com.example.todolistapp.ui.theme.TodoCardColors
import com.example.todolistapp.ui.theme.TodoGreen

// this file has been edited for testing git perpous

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todos: List<TodoEntity>,
    onAddClick: () -> Unit,
    onEditClick: (TodoEntity) -> Unit,
    onDeleteClick: (TodoEntity) -> Unit,
    onToggleClick: (TodoEntity) -> Unit
) {

    // Active first, completed last
    val sortedTodos = remember(todos) {
        todos.sortedBy { it.isCompleted }  // true -> Completed and false -> Active
    }

    // part of UI only state.... not saved in  ViewModel.
    var selectedTodo by remember { mutableStateOf<TodoEntity?>(null) }
    var showDeleteSheet by remember { mutableStateOf(false) }

    // Snackbar state
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    // Show Snackbar when message changes
    LaunchedEffect(snackbarMessage) {  // Snackbar is a suspending function -> used LaunchedEffect
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it)
            snackbarMessage = null
        }
    }

    Scaffold(
        containerColor = LightBg,
        topBar = { TodoListTopBar(onAddClick) },
        snackbarHost = { SnackbarHost(snackbarHostState) } // ✅ ATTACHED
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            itemsIndexed(sortedTodos) { index, todo ->
                TodoItem(
                    todo = todo,
                    index = index,
                    onToggle = {
                        onToggleClick(todo)
                        snackbarMessage =
                            if (todo.isCompleted)
                                "Task marked as active"
                            else
                                "Task completed"
                    },
                    onEdit = {
                        onEditClick(todo)
                        snackbarMessage = "Edit task"
                    },
                    onDelete = {
                        selectedTodo = todo
                        showDeleteSheet = true
                    }
                )
            }
        }
    }

    // DELETE CONFIRMATION
    if (showDeleteSheet && selectedTodo != null) {
        AlertDialog(
            onDismissRequest = { showDeleteSheet = false },
            title = { Text("Delete Task") },
            text = { Text("Do you want to delete this task?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(selectedTodo!!)
                        snackbarMessage = "Task deleted"
                        showDeleteSheet = false
                    }
                ) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteSheet = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

/* ---------------- TOP BAR ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TodoListTopBar(
    onAddClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text("PLANIFY", fontWeight = FontWeight.Bold) },
        actions = {
            IconButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = TodoGreen,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

/* ---------------- TODO ITEM ---------------- */

@Composable
private fun TodoItem(
    todo: TodoEntity,
    index: Int,
    onToggle: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val backgroundColor = TodoCardColors[index % TodoCardColors.size]

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Switch(
                checked = todo.isCompleted,
                onCheckedChange = { onToggle() }
            )

            Spacer(Modifier.width(12.dp))

            Text(
                text = todo.title,
                modifier = Modifier.weight(1f),
                color = if (todo.isCompleted) Color.Gray else Color.Black
            )

            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }

            IconButton(onClick = onDelete) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
