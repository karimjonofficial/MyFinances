package com.orka.myfinances.lib.ui.models

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class ScreenTab(
    val index: Int,
    val title: String,
    val content: (@Composable (Modifier) -> Unit)? = null
)