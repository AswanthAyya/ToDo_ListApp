package com.example.todolistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.navigation.compose.rememberNavController
import com.example.todolistapp.navigation.TodoNavGraph
import com.example.todolistapp.ui.theme.TodoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Text("Hello World")
            TodoTheme {
                val navController = rememberNavController()
                TodoNavGraph(navController)
            }
        }
    }
}
