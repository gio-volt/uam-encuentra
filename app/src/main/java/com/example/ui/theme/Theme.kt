package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = ChampagneGold,
    onPrimary = EditorialBlack,
    primaryContainer = MidnightNavy,
    onPrimaryContainer = Color.White,
    secondary = ChampagneGold,
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF1E2836),
    onSecondaryContainer = Color.White,
    background = Color(0xFF0F141C),
    surface = Color(0xFF161E2A),
    onBackground = Color(0xFFF5F5F4),
    onSurface = Color(0xFFF5F5F4),
    surfaceVariant = Color(0xFF222B38),
    onSurfaceVariant = Color(0xFFA3A3A3),
    outline = Color(0xFF2D3748)
  )

private val LightColorScheme =
  lightColorScheme(
    primary = MidnightNavy,
    onPrimary = Color.White,
    primaryContainer = ChampagneLight,
    onPrimaryContainer = EditorialBlack,
    secondary = ChampagneGold,
    onSecondary = Color.White,
    secondaryContainer = LuxuryCanvasAlt,
    onSecondaryContainer = EditorialBlack,
    tertiary = ChampagneDark,
    onTertiary = Color.White,
    background = LuxuryCanvas,
    surface = LuxurySurface,
    onBackground = EditorialTextPrimary,
    onSurface = EditorialTextPrimary,
    surfaceVariant = LuxuryCanvasAlt,
    onSurfaceVariant = EditorialTextSecondary,
    outline = LuxuryBorder
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Preserve authentic UAM institutional branding
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}

