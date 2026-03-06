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
import com.example.todolistapp.ui.util.isValidTodo   // ✅ ADDED

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoScreen(
    initialText: String,
    onBackClick: () -> Unit,
    onUpdateTodo: (String) -> Unit
) {

    var text by remember { mutableStateOf(initialText) }
    var error by remember { mutableStateOf<String?>(null) }   // ✅ ADDED
    var showDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

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
                        text = "Update TODO",
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
                    error = null        // ✅ CLEAR ERROR WHILE TYPING
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Update your task") },
                isError = error != null
            )

            // ✅ ERROR MESSAGE (NEW)
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
                    if (isValidTodo(text)) {   // ✅ VALIDATION ADDED
                        showDialog = true
                    } else {
                        error = "Todo must be at least 3 words"
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

    /* -------------------- UPDATE CONFIRMATION POPUP -------------------- */

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirm Update") },
            text = { Text("Do you want to update this task?") },
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
