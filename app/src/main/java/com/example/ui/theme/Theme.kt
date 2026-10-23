package com.example.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val TacticalDarkColorScheme = darkColorScheme(
    primary = TacticalAmber,
    onPrimary = TacticalDarkBg,
    primaryContainer = TacticalAmberDark,
    onPrimaryContainer = TacticalTextPrimary,
    secondary = TacticalAmberLight,
    onSecondary = TacticalDarkBg,
    background = TacticalDarkBg,
    onBackground = TacticalTextPrimary,
    surface = TacticalSurface,
    onSurface = TacticalTextPrimary,
    surfaceVariant = TacticalSurfaceVariant,
    onSurfaceVariant = TacticalTextSecondary,
    error = CombatRed,
    onError = TacticalTextPrimary
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = TacticalDarkBg.toArgb()
            window.navigationBarColor = TacticalDarkBg.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = TacticalDarkColorScheme,
        typography = Typography,
        content = content
    )
}
