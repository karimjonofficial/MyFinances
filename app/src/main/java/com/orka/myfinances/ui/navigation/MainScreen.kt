package com.orka.myfinances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager,
    factory: Factory,
    session: Session
) {
    val backStack = navigationManager.backStack.collectAsState()

    NavigationGraph(
        modifier = modifier,
        backStack = backStack.value,
        navigator = navigationManager,
        user = session.user,
        factory = factory
    )
}