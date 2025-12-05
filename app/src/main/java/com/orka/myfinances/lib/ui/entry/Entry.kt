package com.orka.myfinances.lib.ui.entry

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry

fun <T : Any> entry(destination: T, content: @Composable (T) -> Unit) =
    NavEntry(key = destination, content = content)