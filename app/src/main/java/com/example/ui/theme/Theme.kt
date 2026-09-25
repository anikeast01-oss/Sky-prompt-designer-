package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = SkyBlue,
    onPrimary = SkyDeepSpace,
    primaryContainer = SkyCardSurface,
    onPrimaryContainer = SkyBlue,
    secondary = SkyIndigo,
    onSecondary = Color.White,
    secondaryContainer = SkyDeepNavy,
    onSecondaryContainer = SkyIndigo,
    tertiary = SkyCyan,
    onTertiary = SkyDeepSpace,
    background = SkyDeepSpace,
    onBackground = SkyTextPrimary,
    surface = SkyCardDark,
    onSurface = SkyTextPrimary,
    surfaceVariant = SkyCardSurface,
    onSurfaceVariant = SkyTextSecondary,
    outline = SkyCardBorder
)

private val LightColorScheme = lightColorScheme(
    primary = SkyLightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE0F2FE),
    onPrimaryContainer = SkyLightPrimary,
    secondary = SkyIndigo,
    onSecondary = Color.White,
    tertiary = SkyCyan,
    background = SkyLightBackground,
    onBackground = SkyLightTextPrimary,
    surface = SkyLightSurface,
    onSurface = SkyLightTextPrimary,
    surfaceVariant = SkyLightSurfaceVariant,
    onSurfaceVariant = SkyLightTextSecondary,
    outline = Color(0xFFCBD5E1)
)

@Composable
fun SkyPromptTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
