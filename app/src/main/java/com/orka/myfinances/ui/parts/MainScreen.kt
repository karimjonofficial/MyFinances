package com.orka.myfinances.ui.parts

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.orka.myfinances.NavigationManagerImpl
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.navigation.NavigationGraph

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    dialogManager: DialogManager,
    navigationManager: NavigationManagerImpl,
    session: Session
) {
    val backStack = navigationManager.backStack.collectAsState()
    val dialogState = dialogManager.dialogState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                destination = backStack.value.last(),
                session = session,
                dialogManager = dialogManager,
                navigationManager = navigationManager
            )
        },
        bottomBar = {
            val state = navigationManager.navigationState.collectAsState()
            val backstack = navigationManager.backStack.collectAsState()
            val visible = remember {
                derivedStateOf {
                    backstack.value.last().hasNavBar
                }
            }

            if(visible.value) {
                MainNavBar(
                    destination = state.value,
                    navigationManager = navigationManager
                )
            }
        }
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