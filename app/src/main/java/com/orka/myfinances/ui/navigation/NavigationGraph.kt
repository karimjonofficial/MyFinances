package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.data.models.User
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.managers.Navigator

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    user: User,
    backStack: List<Destination>,
    navigator: Navigator,
    factory: Factory
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { navigator.back() },
        entryProvider = { destination ->
            entryProvider(
                modifier = Modifier.fillMaxSize(),
                user = user,
                destination = destination,
                navigator = navigator,
                factory = factory
            )
        }
    )
}