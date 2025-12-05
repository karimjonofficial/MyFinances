package com.orka.myfinances.ui.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.impl.ui.managers.NavigationManagerImpl
import com.orka.myfinances.ui.navigation.NavigationGraph

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    navigationManager: NavigationManagerImpl,
    session: Session
) {
    val backStack = navigationManager.backStack.collectAsState()

    NavigationGraph(
        modifier = modifier,
        backStack = backStack.value,
        navigationManager = navigationManager,
        user = session.user
    )
}