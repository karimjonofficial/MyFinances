package com.orka.myfinances.lib.extensions.ui

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.IconButtonColors

fun ButtonColors.toIconButtonColors(): IconButtonColors {
    return IconButtonColors(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor
    )
}