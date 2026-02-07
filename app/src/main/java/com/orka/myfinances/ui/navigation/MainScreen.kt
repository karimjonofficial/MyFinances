package com.orka.myfinances.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.screens.host.NavigationManager

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    navigationManager: NavigationManager,
    factory: Factory,
    session: Session,
    sessionManager: SessionManager
) {
    val backStack = navigationManager.backStack.collectAsState()

    NavigationGraph(
        modifier = modifier,
        backStack = backStack.value,
        navigator = navigationManager,
        session = session,
        sessionManager = sessionManager,
        factory = factory
    )
}