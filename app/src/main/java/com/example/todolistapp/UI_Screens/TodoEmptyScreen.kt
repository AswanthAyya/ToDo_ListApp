package com.example.todolistapp.UI_Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.todolistapp.ui.theme.AccentPink
import com.example.todolistapp.ui.theme.LightBg
import com.example.todolistapp.ui.theme.TodoGreen

// Home Screen ()
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoEmptyScreen(
    onAddClick: () -> Unit
) {
    Scaffold(
        containerColor = LightBg,
        topBar = {
            CustomTodoTopBar(onAddClick)
        }
    ) { paddingValues ->
        EmptyTodoContent(
            modifier = Modifier.padding(paddingValues),
            onAddClick = onAddClick
        )
    }
}

// Top Bar Customised
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTodoTopBar(
    onAddClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "PLANIFY",fontWeight = FontWeight.Bold)
        },

        actions = {
            IconButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Default.Add,contentDescription = "Add Todo")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = TodoGreen,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}

/* ------------------------- EMPTY CONTENT ------------------------- */

@Composable
private fun EmptyTodoContent(
    modifier: Modifier = Modifier,
    onAddClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "You don't have a TODOs",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(20.dp))

            CustomAddTodoButton(onAddClick)
        }
    }
}

// Custom Add Todo Button

@Composable
private fun CustomAddTodoButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = AccentPink
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .height(48.dp)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Add Todo",
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}
