package com.chantiercontrole.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CouleursChantier = lightColorScheme(
    primary = Color(0xFF1F4E79),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFFF28C28),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFF5F7FA),
    onBackground = Color(0xFF1A1A1A),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1A1A),
    error = Color(0xFFB3261E)
)

@Composable
fun ChantierControleTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CouleursChantier,
        content = content
    )
}
