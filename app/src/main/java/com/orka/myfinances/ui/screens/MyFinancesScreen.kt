package com.orka.myfinances.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.managers.UiState
import com.orka.myfinances.ui.screens.main.LoadingScreen
import com.orka.myfinances.ui.screens.main.MainScreen
import com.orka.myfinances.ui.screens.login.LoginScreen

@Composable
fun MyFinancesScreen(
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
            MainScreen(modifier, state)
        }
    }
}