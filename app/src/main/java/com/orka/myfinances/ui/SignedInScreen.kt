package com.orka.myfinances.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.UiState
import com.orka.myfinances.ui.screens.home.parts.HomeScreenTopBar

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SignedInScreen(
    modifier: Modifier = Modifier,
    state: UiState.SignedIn
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = { HomeScreenTopBar { dialogVisible.value = true } }
    ) { innerPadding ->
        val viewModel = state.viewModel

        NavigationGraph(
            modifier = Modifier.padding(innerPadding),
            dialogVisible = dialogVisible,
            viewModel = viewModel
        )
    }
}

