package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = AtlasCyan,
    onPrimary = AtlasDarkBg,
    primaryContainer = AtlasSurfaceVariant,
    onPrimaryContainer = AtlasTextPrimary,
    secondary = AtlasViolet,
    onSecondary = AtlasDarkBg,
    tertiary = AtlasGreen,
    background = AtlasDarkBg,
    onBackground = AtlasTextPrimary,
    surface = AtlasSurface,
    onSurface = AtlasTextPrimary,
    surfaceVariant = AtlasSurfaceVariant,
    onSurfaceVariant = AtlasTextSecondary,
    outline = AtlasBorder,
    error = AtlasRed
)

private val LightColorScheme = lightColorScheme(
    primary = AtlasLightPrimary,
    onPrimary = AtlasLightSurface,
    primaryContainer = AtlasLightSurfaceVariant,
    onPrimaryContainer = AtlasLightTextPrimary,
    secondary = AtlasViolet,
    onSecondary = AtlasLightSurface,
    tertiary = AtlasGreen,
    background = AtlasLightBg,
    onBackground = AtlasLightTextPrimary,
    surface = AtlasLightSurface,
    onSurface = AtlasLightTextPrimary,
    surfaceVariant = AtlasLightSurfaceVariant,
    onSurfaceVariant = AtlasLightTextSecondary,
    outline = AtlasLightBorder,
    error = AtlasRed
)

@Composable
fun CodeAtlasTheme(
    darkTheme: Boolean = true, // Default to dark developer theme
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
