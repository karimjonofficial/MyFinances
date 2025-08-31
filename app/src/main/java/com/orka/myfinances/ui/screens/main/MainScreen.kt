package com.orka.myfinances.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.ui.navigation.Destinations
import com.orka.myfinances.ui.navigation.NavigationGraph
import com.orka.myfinances.ui.parts.MainNavBar
import com.orka.myfinances.ui.parts.MainTopBar
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel,
    session: Session
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val backStack = remember { mutableStateListOf<Destinations>(Destinations.Home(null)) }

    Scaffold(
        modifier = modifier,
        topBar = {
            MainTopBar(
                backStack = backStack,
                session = session
            )
        },
        bottomBar = { MainNavBar(backStack = backStack) }
    ) { innerPadding ->

        NavigationGraph(
            modifier = Modifier.padding(innerPadding),
            dialogVisible = dialogVisible,
            backStack = backStack,
            viewModel = viewModel
        )
    }
}