package com.orka.myfinances.lib.ui.entry

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry

fun <T : Any> entry(
    modifier: Modifier = Modifier,
    destination: T,
    content: @Composable (T) -> Unit
): NavEntry<T> {
    return NavEntry(destination) {
        Surface(modifier = modifier) {
            content(destination)
        }
    }
}