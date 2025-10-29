package com.jcu.focusgarden.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryGreen,
    onPrimary = SurfaceWhite,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = PrimaryGreenDark,
    
    secondary = ProgressGreenStart,
    onSecondary = SurfaceWhite,
    secondaryContainer = SecondaryContainer,
    onSecondaryContainer = PrimaryGreenDark,
    
    tertiary = PersonalOrange,
    onTertiary = SurfaceWhite,
    
    error = ErrorRed,
    onError = SurfaceWhite,
    
    background = BackgroundLight,
    onBackground = TextPrimary,
    
    surface = SurfaceWhite,
    onSurface = TextPrimary,
    surfaceVariant = PrimaryContainer,
    onSurfaceVariant = TextSecondary,
    
    outline = OutlineGray,
    outlineVariant = OutlineGray.copy(alpha = 0.5f)
)

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryGreenLight,
    onPrimary = PrimaryGreenDark,
    primaryContainer = PrimaryGreenDark,
    onPrimaryContainer = PrimaryContainer,
    
    secondary = ProgressGreenEnd,
    onSecondary = PrimaryGreenDark,
    secondaryContainer = PrimaryGreenDark,
    onSecondaryContainer = SecondaryContainer,
    
    tertiary = PersonalOrangeLight,
    onTertiary = PrimaryGreenDark,
    
    error = ErrorRed,
    onError = SurfaceWhite,
    
    background = TextPrimary,
    onBackground = SurfaceWhite,
    
    surface = TextPrimary,
    onSurface = SurfaceWhite,
    surfaceVariant = PrimaryGreenDark,
    onSurfaceVariant = OutlineGray,
    
    outline = OutlineGray
)

@Composable
fun FocusGardenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

