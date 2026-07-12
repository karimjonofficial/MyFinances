package com.orka.myfinances.ui.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.application.manager.runtime.GuestRuntimeInitializerImpl
import com.orka.myfinances.application.manager.runtime.NewUserRuntimeInitializerImpl
import com.orka.myfinances.application.manager.runtime.SignedInRuntimeInitializerImpl
import com.orka.myfinances.application.manager.ui.FailureType
import com.orka.myfinances.application.manager.ui.UiState
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.navigation.MainScreen
import com.orka.myfinances.ui.screens.host.viewmodel.HostScreenInteractor
import com.orka.myfinances.ui.screens.login.LoginScreen

@Composable
fun HostScreen(
    modifier: Modifier = Modifier,
    state: UiState,
    guestRuntimeInitializer: GuestRuntimeInitializerImpl,
    newUserRuntimeInitializer: NewUserRuntimeInitializerImpl,
    signedInRuntimeInitializer: SignedInRuntimeInitializerImpl,
    interactor: HostScreenInteractor
) {
    when (state) {
        is UiState.Initial -> SplashScreen(modifier)

        is UiState.Loading -> LoadingScreen(
            modifier = modifier
        )

        is UiState.Guest -> {
            val viewModel = viewModel {
                guestRuntimeInitializer.factory().get()
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
                key = state.credentials.access,
                initializer = { newUserRuntimeInitializer.factory().get(state.companyId) }
            )
            val uiState = viewModel.uiState.collectAsState()

            SelectBranchScreen(
                modifier = modifier,
                state = uiState.value,
                interactor = viewModel
            )
        }

        is UiState.Failure -> {
            val res = if (state.type is FailureType.UnSpecified) R.string.failure else R.string.failure_unauthorized
            val message = stringResource(res)

            FailureScreen(
                modifier = modifier,
                retry = interactor::refresh,
                message = message
            )
        }

        is UiState.SignedIn -> MainScreen(
            modifier = modifier,
            navigationManager = signedInRuntimeInitializer.navigator(),
            session = state.session,
            factory = signedInRuntimeInitializer.factory()
        )
    }
}