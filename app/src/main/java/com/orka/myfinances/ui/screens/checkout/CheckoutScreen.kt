package com.orka.myfinances.ui.screens.checkout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    interactor: CheckoutScreenInteractor,
    state: State<CheckoutScreenModel>,
    uiState: CheckoutUIState,
    content: @Composable (Modifier, CheckoutScreenModel) -> Unit
) {
    LaunchedEffect(state) {
        if (state is State.Success) {
            if (uiState.price == null) uiState.price = state.value.exposedPrice
        }
    }

    StatefulScreen(
        modifier = modifier,
        topBar = {
            CheckoutScreenTopBar(
                exposed = uiState.exposed,
                onExposedChange = { uiState.exposed = !uiState.exposed }
            )
        },
        bottomBar = {
            if (it is State.Success) {
                CheckoutScreenBottomBar(
                    uiState = uiState,
                    interactor = interactor
                )
            }
        },
        state = state,
        onRetry = interactor::refresh
    ) { modifier, model ->
        content(modifier, model)
    }
}
