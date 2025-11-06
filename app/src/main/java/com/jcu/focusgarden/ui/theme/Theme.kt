package com.jcu.focusgarden.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
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
    primary = DarkPrimaryGreen,           // 明亮绿色
    onPrimary = Color(0xFF003300),        // 深色文字在主色上
    primaryContainer = DarkPrimaryContainer, // 深绿色容器
    onPrimaryContainer = DarkPrimaryGreenLight,
    
    secondary = DarkPrimaryGreenDark,     // 次级绿色
    onSecondary = Color(0xFF003300),
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkPrimaryGreenLight,
    
    tertiary = PersonalOrangeLight,
    onTertiary = Color(0xFF003300),
    
    error = ErrorRed,
    onError = SurfaceWhite,
    
    background = DarkBackground,          // 深灰背景 #121212
    onBackground = DarkTextPrimary,       // 浅色文字
    
    surface = DarkSurface,                // 深灰表面 #1E1E1E
    onSurface = DarkTextPrimary,          // 浅色文字
    surfaceVariant = DarkSurfaceVariant,  // 变体表面
    onSurfaceVariant = DarkTextSecondary, // 次级浅色文字
    
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant
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

