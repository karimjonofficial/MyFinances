package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scrim
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    session: Session,
    backStack: List<Destination>,
    navigator: Navigator,
    factory: Factory
) {
    val prevBackStackSize = remember { mutableIntStateOf(backStack.size) }

    val lastActionWasPop = remember(backStack) {
        val isPop = backStack.size < prevBackStackSize.intValue
        prevBackStackSize.intValue = backStack.size
        isPop
    }

    NavDisplay(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        backStack = backStack,
        onBack = { navigator.back() },
        entryProvider = { destination ->
            val originalEntry = entryProvider(
                modifier = Modifier.fillMaxSize(),
                session = session,
                destination = destination,
                factory = factory
            )

            NavEntry(navEntry = originalEntry) {
                val lifecycleOwner = LocalLifecycleOwner.current
                val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
                val isTransitioning = lifecycleState == Lifecycle.State.STARTED
                
                Box(Modifier.fillMaxSize()) {
                    originalEntry.Content()

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
        }
    )
}