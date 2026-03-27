package com.orka.myfinances.ui.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.navigation.MainScreen
import com.orka.myfinances.ui.screens.host.viewmodel.HostScreenInteractor
import com.orka.myfinances.ui.screens.host.viewmodel.UiState
import com.orka.myfinances.ui.screens.login.LoginScreen

@Composable
fun HostScreen(
    modifier: Modifier = Modifier,
    state: UiState,
    interactor: HostScreenInteractor
) {
    when (state) {
        is UiState.Initial -> SplashScreen(modifier)

        is UiState.Guest -> {
            val viewModel = state.viewModel
            val uiState = viewModel.uiState.collectAsState()

            LoginScreen(
                modifier = modifier,
                state = uiState.value,
                interactor = viewModel
            )
        }

        is UiState.NewUser -> {
            val viewModel = state.viewModel
            val uiState = viewModel.uiState.collectAsState()

            SelectOfficeScreen(
                modifier = modifier,
                state = uiState.value,
                interactor = viewModel
            )
        }

        is UiState.Failure -> {
            FailureScreen(
                modifier = modifier,
                message = state.message.str()
            )
        }

        is UiState.SignedIn -> MainScreen(
            modifier = modifier,
            navigationManager = state.navigationManager,
            session = state.session,
            factory = state.factory,
            sessionManager = interactor
        )

        is UiState.Loading -> LoadingScreen(modifier)
    }
}