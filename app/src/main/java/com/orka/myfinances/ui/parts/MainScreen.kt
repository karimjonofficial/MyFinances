package com.orka.myfinances.ui.parts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.navigation.NavigationManager
import com.orka.myfinances.ui.navigation.NavigationGraph

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
        navigationManager = navigationManager,
        user = session.user,
        factory = factory
    )
}