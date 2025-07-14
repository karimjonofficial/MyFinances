package com.orka.myfinances.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.UiState
import com.orka.myfinances.lib.LoadingScreen
import com.orka.myfinances.ui.screens.login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyFinancesContent(
    modifier: Modifier = Modifier,
    state: UiState
) {
    when (state) {
        UiState.Initial -> {
            LoadingScreen(modifier = modifier)
        }

        is UiState.Guest -> {
            val uiState = state.viewModel.uiState.collectAsState()

            LoginScreen(
                modifier = modifier,
                uiState = uiState.value,
                viewModel = state.viewModel
            )
        }

        is UiState.SignedIn -> {
            SignedInScreen(modifier, state)
        }
    }
}