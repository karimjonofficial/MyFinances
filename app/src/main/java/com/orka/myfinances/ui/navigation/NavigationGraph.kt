package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.ui.NavDisplay
import com.orka.myfinances.data.models.User
import com.orka.myfinances.factories.viewmodel.Factory
import com.orka.myfinances.impl.ui.managers.NavigationManager
import com.orka.myfinances.ui.managers.navigation.Destination

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    user: User,
    backStack: List<Destination>,
    navigationManager: NavigationManager,
    factory: Factory
) {
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { navigationManager.back() },
        entryProvider = { destination ->
            entryProvider(
                modifier = Modifier.fillMaxSize(),
                user = user,
                destination = destination,
                navigator = navigationManager,
                factory = factory
            )
        }
    )
}