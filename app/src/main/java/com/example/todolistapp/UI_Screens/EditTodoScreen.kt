package com.example.todolistapp.UI_Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolistapp.ui.theme.AccentPink
import com.example.todolistapp.ui.theme.LightBg
import com.example.todolistapp.ui.theme.TodoGreen
import kotlinx.coroutines.launch
import com.example.todolistapp.ui.util.isValidTodo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(
    initialText: String,
    onBackClick: () -> Unit,
    onUpdateTodo: (String) -> Unit
) {

    //Local UI State
    var text by remember { mutableStateOf(initialText) }
    var error by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // create a new viewmodel, and put all states here.

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // SnackBar is a Suspending function

    Scaffold(
        containerColor = LightBg,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
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
                        text = "UPDATE YOUR TODO TASK",
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
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    error = null
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Update your task") },
                isError = error != null
            )

            if (error != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (isValidTodo(text)) {
                        showDialog = true
                    } else {
                        error = "Todo Task must be at least 3 words"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AccentPink),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = "Update",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }

    // Conformation Popup
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Update") },
            text = { Text("Do you want to update this todo task?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onUpdateTodo(text)

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Task updated",
                                duration = SnackbarDuration.Long
                            )
                        }
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        onBackClick()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}
