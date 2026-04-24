package com.continuum.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val ContinuumColorScheme = darkColorScheme(
    primary = Teal,
    onPrimary = Bg0,
    primaryContainer = B1,
    onPrimaryContainer = T1,
    secondary = BlueAccent,
    onSecondary = T1,
    secondaryContainer = B2,
    onSecondaryContainer = T1,
    tertiary = PurpleRole,
    onTertiary = T1,
    background = Bg0,
    onBackground = T1,
    surface = Bg1,
    onSurface = T1,
    surfaceVariant = Bg2,
    onSurfaceVariant = T2,
    outline = B1,
    outlineVariant = B2,
    error = Red,
    onError = T1,
    errorContainer = Red.copy(alpha = 0.15f),
    onErrorContainer = Red,
    inverseSurface = T1,
    inverseOnSurface = Bg0,
    inversePrimary = Bg0,
    scrim = Bg0.copy(alpha = 0.88f),
)

@Composable
fun ContinuumTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = ContinuumColorScheme,
        typography = ContinuumTypography,
        content = content
    )
}
