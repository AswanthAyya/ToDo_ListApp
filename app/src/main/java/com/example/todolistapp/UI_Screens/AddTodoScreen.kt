package com.example.todolistapp.UI_Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolistapp.ui.theme.AccentPink
import com.example.todolistapp.ui.theme.LightBg
import com.example.todolistapp.ui.theme.TodoGreen
import com.example.todolistapp.ui.util.isValidTodo


// Add Task Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoScreen(
    onBackClick: () -> Unit,
    onAddTodo: (String) -> Unit
) {

    // Local UI State
    var todoText by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = LightBg,
        topBar = {
            AddTodoTopBar(onBackClick)
        }
    ) { paddingValues ->
        AddTodoContent(
            modifier = Modifier.padding(paddingValues),
            todoText = todoText,
            error = error,
            onTextChange = {
                todoText = it
                error = null
            },
            onAddClick = {
                if (isValidTodo(todoText)) {
                    onAddTodo(todoText)
                } else {
                    error = "Todo must be at least 3 words"
                }
            }
        )
    }
}

// Top Bar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddTodoTopBar(
    onBackClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Text(
                text = "Add Todo",
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = TodoGreen,
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}

// Add Todo Screen Content
@Composable
private fun AddTodoContent(
    modifier: Modifier = Modifier,
    todoText: String,
    error: String?,
    onTextChange: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = todoText,
            onValueChange = onTextChange,
            placeholder = { Text("Enter your todo here") },
            isError = error != null,
            modifier = Modifier.fillMaxWidth()
        )

        if (error != null) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        AddTodoButton(onClick = onAddClick)
    }
}

// Add Todo Button -> Customised Submit Button\
@Composable
private fun AddTodoButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.buttonElevation(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
    ) {
        Text(
            text = "Add Todo",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}
