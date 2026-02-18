package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun CheckoutScreen(
    modifier: Modifier,
    viewModel: CheckoutScreenViewModel,
    state: CheckoutScreenState
) {
    when (state) {
        is CheckoutScreenState.Loading -> {
            Scaffold(
                modifier = modifier,
                title = stringResource(R.string.checkout)
            ) { paddingValues ->
                LoadingScreen(modifier = Modifier.scaffoldPadding(paddingValues))
            }
        }

        is CheckoutScreenState.Failure -> {
            Scaffold(
                modifier = modifier,
                title = stringResource(R.string.checkout)
            ) { paddingValues ->
                FailureScreen(modifier = Modifier.scaffoldPadding(paddingValues))
            }
        }

        is CheckoutScreenState.Success -> {
            CheckoutContent(
                modifier = modifier,
                items = state.items,
                clients = state.clients,
                price = state.price,
                viewModel = viewModel
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
    val viewModel = viewModel {
        CheckoutScreenViewModel(
            addSale = { null },
            addOrder = { null },
            basketRepository = BasketRepository(getById = { null }),
            get = { null },
            logger = DummyLogger(),
            navigator = DummyNavigator(),
            formatPrice = {""},
            formatDecimal = {""}
        )
    }
    val items = listOf(
        BasketItemCardModel("Product1", "10,000.00 UZS x 10 = 100,000.00 UZS"),
        BasketItemCardModel("Product2", "10,000.00 UZS x 10 = 100,000.00 UZS")
    )

    MyFinancesTheme {
        CheckoutScreen(
            modifier = Modifier.fillMaxSize(),
            state = CheckoutScreenState.Success(clients, items, 10000),
            viewModel = viewModel
        )
    }
}