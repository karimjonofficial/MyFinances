package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.Scaffold
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.checkout.viewmodel.BasketItemCardModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun CheckoutScreen(
    modifier: Modifier,
    interactor: CheckoutScreenInteractor,
    state: State<CheckoutScreenModel>
) {
    when (state) {
        is State.Loading -> {
            Scaffold(
                modifier = modifier,
                title = stringResource(R.string.checkout)
            ) { paddingValues ->
                LoadingScreen(modifier = Modifier.scaffoldPadding(paddingValues))
            }
        }

        is State.Failure -> {
            Scaffold(
                modifier = modifier,
                title = stringResource(R.string.checkout)
            ) { paddingValues ->
                FailureScreen(modifier = Modifier.scaffoldPadding(paddingValues))
            }
        }

        is State.Success -> {
            val state = state.value

            CheckoutContent(
                modifier = modifier,
                items = state.items,
                clients = state.clients,
                price = state.price,
                printerConnected = state.printerConnected,
                interactor = interactor
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun CheckoutScreenPreview() {
    val items = listOf(
        BasketItemCardModel("Product1", "10,000.00 UZS x 10 = 100,000.00 UZS"),
        BasketItemCardModel("Product2", "10,000.00 UZS x 10 = 100,000.00 UZS")
    )
    val state = State.Success(
        CheckoutScreenModel(
            clients = clients.map { it.map() },
            items = items,
            price = 10000,
            printerConnected = false
        )
    )

    MyFinancesTheme {
        CheckoutScreen(
            modifier = Modifier.fillMaxSize(),
            state = state,
            interactor = CheckoutScreenInteractor.dummy
        )
    }
}