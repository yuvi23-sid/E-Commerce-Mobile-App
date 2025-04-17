package com.example.homepage.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val BackgroundColor = Color(0xFFE4E0E1)
val SurfaceColor = Color(0xFFD6C0B3)
val PrimaryColor = Color(0xFFAB886D)
val TextColor = Color(0xFF493628)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    background = BackgroundColor,
    surface = SurfaceColor,
    onPrimary = Color.White,     // Text/icons on primary buttons
    onBackground = TextColor,    // Default text color
    onSurface = TextColor,       // Text on cards
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryColor,
    background = Color.Black,
    surface = Color.DarkGray,
    onPrimary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun HomePageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}