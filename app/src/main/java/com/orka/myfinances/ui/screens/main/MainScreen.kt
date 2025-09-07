package com.orka.myfinances.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.navigation.Destinations
import com.orka.myfinances.ui.managers.dialog.DialogManager
import com.orka.myfinances.ui.navigation.NavigationGraph
import com.orka.myfinances.ui.parts.MainNavBar
import com.orka.myfinances.ui.parts.MainTopBar
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    dialogManager: DialogManager,
    viewModel: HomeScreenViewModel,
    session: Session
) {
    val backStack = remember { mutableStateListOf<Destinations>(Destinations.Home(null, viewModel)) }
    val dialogState = dialogManager.dialogState.collectAsState()

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                backStack = backStack,
                session = session,
                dialogManager = dialogManager
            )
        },
        bottomBar = { MainNavBar(backStack = backStack) }
    ) { innerPadding ->
        NavigationGraph(
            modifier = Modifier.padding(innerPadding),
            dialogState = dialogState.value,
            backStack = backStack,
            dialogManager = dialogManager
        )
    }
}