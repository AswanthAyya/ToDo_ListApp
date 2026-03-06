package com.example.todolistapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color

@Composable
fun TodoTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = TodoGreen,
            onPrimary = Color.White,
            background = LightBg,
            onBackground = Color.Black
        ),
        content = content
    )
}
