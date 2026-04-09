package com.orka.myfinances.ui.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.lib.extensions.ui.str
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.navigation.MainScreen
import com.orka.myfinances.ui.screens.host.viewmodel.UiState
import com.orka.myfinances.ui.screens.login.LoginScreen

@Composable
fun HostScreen(
    modifier: Modifier = Modifier,
    state: UiState
) {
    when (state) {
        is UiState.Initial -> SplashScreen(modifier)

        is UiState.Guest -> {
            val viewModel = viewModel {
                state.factory.get()
            }
            val uiState = viewModel.uiState.collectAsState()

            LoginScreen(
                modifier = modifier,
                state = uiState.value,
                interactor = viewModel
            )
        }

        is UiState.NewUser -> {
            val viewModel = viewModel(
                key = "${state.companyId.value}",
                initializer = { state.factory.get(state.companyId) }
            )
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
            factory = state.factory
        )

        is UiState.Loading -> LoadingScreen(modifier)
    }
}