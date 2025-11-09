package com.example.examenfinal.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

// Paleta sencilla, puedes cambiar colores si quieres
private val LightColors = lightColorScheme()

@Composable
fun ExamenFinalTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
