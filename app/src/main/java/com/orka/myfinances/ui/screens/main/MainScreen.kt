package com.orka.myfinances.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.navigation.NavigationGraph
import com.orka.myfinances.ui.parts.MainNavBar
import com.orka.myfinances.ui.parts.MainTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    dialogManager: DialogManager,
    navigationManager: NavigationManager,
    session: Session
) {
    val backStack = navigationManager.backStack.collectAsState()
    val dialogState = dialogManager.dialogState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                currentDestination = backStack.value.last(),
                session = session,
                dialogManager = dialogManager
            )
        },
        bottomBar = { MainNavBar(navigationManager = navigationManager) }
    ) { innerPadding ->

        NavigationGraph(
            modifier = Modifier.padding(innerPadding),
            dialogState = dialogState.value,
            backStack = backStack.value,
            navigationManager = navigationManager,
            dialogManager = dialogManager
        )
    }
}