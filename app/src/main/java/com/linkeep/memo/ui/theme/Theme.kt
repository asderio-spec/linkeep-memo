package com.linkeep.memo.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

// Gmarket Design System colors
private object GDSColors {
    val PrimaryGreen = Color(0xFF00C73C)
    val PrimaryGreenDark = Color(0xFF009E2E)
    val PrimaryGreenLight = Color(0xFF4FD66E)

    val SecondaryRed = Color(0xFFFF3D32)

    // Gray scale
    val Gray50 = Color(0xFFF8F9FA)
    val Gray100 = Color(0xFFF1F3F5)
    val Gray200 = Color(0xFFE9ECEF)
    val Gray600 = Color(0xFF868E96)
    val Gray700 = Color(0xFF495057)
    val Gray900 = Color(0xFF212529)
}

private val LightColors = lightColorScheme(
    primary = GDSColors.PrimaryGreen,
    onPrimary = Color.White,
    primaryContainer = GDSColors.PrimaryGreenLight,
    onPrimaryContainer = Color(0xFF003915),

    secondary = GDSColors.Gray700,
    onSecondary = Color.White,
    secondaryContainer = GDSColors.Gray100,
    onSecondaryContainer = GDSColors.Gray900,

    background = Color.White,
    onBackground = GDSColors.Gray900,
    surface = Color.White,
    onSurface = GDSColors.Gray900,

    outline = GDSColors.Gray200
)

private val DarkColors = darkColorScheme(
    primary = GDSColors.PrimaryGreen,
    onPrimary = Color.Black,
    primaryContainer = GDSColors.PrimaryGreenDark,
    onPrimaryContainer = Color.White,

    secondary = GDSColors.Gray200,
    onSecondary = Color.Black,
    secondaryContainer = GDSColors.Gray700,
    onSecondaryContainer = Color.White,

    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF121212),
    onSurface = Color.White,

    outline = GDSColors.Gray700
)

// Typography aligned to Gmarket token scale
private val GmarketTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    )
)

private val GmarketShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(6.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(12.dp),
    extraLarge = RoundedCornerShape(16.dp)
)

@Composable
fun MemoTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = GmarketTypography,
        shapes = GmarketShapes,
        content = content
    )
}