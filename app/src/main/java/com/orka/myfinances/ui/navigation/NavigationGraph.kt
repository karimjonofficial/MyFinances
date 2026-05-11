package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
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
    key(session.officeId.value) {
        NavDisplay(
            modifier = modifier.background(MaterialTheme.colorScheme.background),
            backStack = backStack,
            onBack = { navigator.back() },
            entryProvider = { destination ->
                entryProvider(
                    modifier = Modifier.fillMaxSize(),
                    session = session,
                    destination = destination,
                    factory = factory
                )
            }
        )
    }
}