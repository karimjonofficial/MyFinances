package com.orka.myfinances.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

val LightColorScheme = lightColorScheme(
    primary            = Blue40,
    onPrimary          = Neutral99,
    primaryContainer   = Blue90,
    onPrimaryContainer = Blue10,

    secondary            = Green40,
    onSecondary          = Neutral99,
    secondaryContainer   = Green90,
    onSecondaryContainer = Green10,

    tertiary            = Amber40,
    onTertiary          = Neutral99,
    tertiaryContainer   = Amber90,
    onTertiaryContainer = Neutral10,

    error            = Red40,
    onError          = Neutral99,
    errorContainer   = Red90,
    onErrorContainer = Red10,

    background   = Neutral99,
    onBackground = Neutral10,

    surface            = Neutral99,
    onSurface          = Neutral10,
    surfaceVariant     = NeutralVar90,
    onSurfaceVariant   = NeutralVar30,
    surfaceContainer       = Blue95,
    surfaceContainerHigh   = NeutralVar90,
    surfaceContainerHighest = Neutral90,

    outline        = NeutralVar50,
    outlineVariant = NeutralVar80,
)

val DarkColorScheme = darkColorScheme(
    primary            = Blue80,
    onPrimary          = Blue20,
    primaryContainer   = Blue30,
    onPrimaryContainer = Blue90,

    secondary            = Green80,
    onSecondary          = Green20,
    secondaryContainer   = Green20,
    onSecondaryContainer = Green90,

    tertiary            = Amber80,
    onTertiary          = Neutral10,
    tertiaryContainer   = Amber40,
    onTertiaryContainer = Amber90,

    error            = Red80,
    onError          = Red10,
    errorContainer   = Red40,
    onErrorContainer = Red90,

    background   = Neutral10,
    onBackground = Neutral90,

    surface            = Neutral10,
    onSurface          = Neutral90,
    surfaceVariant     = NeutralVar30,
    onSurfaceVariant   = NeutralVar80,
    surfaceContainer       = Neutral20,
    surfaceContainerHigh   = Neutral30,
    surfaceContainerHighest = NeutralVar30,

    outline        = NeutralVar50,
    outlineVariant = NeutralVar30,
)

@Composable
fun MyFinancesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        shapes      = shapes,
        typography  = Typography,
        content     = content
    )
}