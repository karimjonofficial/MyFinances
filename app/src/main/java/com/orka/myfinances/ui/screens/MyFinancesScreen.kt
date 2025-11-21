package com.orka.myfinances.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.managers.session.UiState
import com.orka.myfinances.ui.parts.MainScreen
import com.orka.myfinances.ui.screens.login.LoginScreen

@Composable
fun MyFinancesScreen(
    modifier: Modifier = Modifier,
    state: UiState
) {
    when (state) {
        UiState.Initial -> {
            SplashScreen(modifier = modifier)
        }

        is UiState.Guest -> {
            val viewModel = state.viewModel
            val uiState = viewModel.uiState.collectAsState()

            LoginScreen(
                modifier = modifier,
                uiState = uiState.value,
                viewModel = viewModel
            )
        }

        is UiState.SignedIn -> {

            MainScreen(
                modifier = modifier,
                dialogManager = state.dialogManager,
                navigationManager = state.navigationManager,
                session = state.session
            )
        }
    }
}