package com.orka.myfinances.ui.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.MainScreen
import com.orka.myfinances.ui.screens.login.LoginScreen

@Composable
fun HostScreen(
    modifier: Modifier = Modifier,
    state: UiState,
    sessionManager: SessionManager
) {
    when (state) {
        UiState.Initial -> SplashScreen(modifier)

        is UiState.Guest -> {
            val viewModel = state.viewModel
            val uiState = viewModel.uiState.collectAsState()

            LoginScreen(
                modifier = modifier,
                uiState = uiState.value,
                viewModel = viewModel
            )
        }

        is UiState.SignedIn -> MainScreen(
            modifier = modifier,
            navigationManager = state.navigationManager,
            session = state.session,
            factory = state.factory,
            sessionManager = sessionManager
        )
    }
}