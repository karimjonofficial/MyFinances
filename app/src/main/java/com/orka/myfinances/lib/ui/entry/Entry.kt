package com.orka.myfinances.lib.ui.entry

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scrim
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.navigation.Destination

fun <T : Any> entry(
    destination: T,
    backStack: List<Destination>,
    lastActionWasPop: Boolean,
    content: @Composable (T) -> Unit
): NavEntry<T> {
    return NavEntry(
        key = destination,
        content = {
            val lifecycleOwner = LocalLifecycleOwner.current
            val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
            val isTransitioning = lifecycleState == Lifecycle.State.STARTED

            Box(Modifier.fillMaxSize()) {
                content(destination)

                if (isTransitioning) {
                    val isTop = destination == backStack.lastOrNull()
                    val shouldHaveScrim = if (lastActionWasPop) isTop else !isTop

                    if (shouldHaveScrim) {
                        Scrim(
                            contentDescription = null,
                            color = Color.Black.copy(alpha = 0.4f),
                            onClick = null
                        )
                    }
                }
            }
        }
    )
}

fun <T : Any> entry(destination: T, content: @Composable (T) -> Unit) =
    NavEntry(key = destination, content = content)