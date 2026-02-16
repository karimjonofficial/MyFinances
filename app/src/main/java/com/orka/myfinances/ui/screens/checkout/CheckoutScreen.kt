package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.basket.basketItems
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.viewmodel.list.State

@Composable
fun CheckoutScreen(
    modifier: Modifier,
    viewModel: CheckoutScreenViewModel,
    items: List<BasketItem>
) {
    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is State.Initial, is State.Loading -> {
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

        is State.Success<*> -> {
            CheckoutContent(
                modifier = modifier,
                items = items,
                clients = state.value as List<Client>,
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
    CheckoutScreen(
        modifier = Modifier.fillMaxSize(),
        items = basketItems,
        viewModel = viewModel {
            CheckoutScreenViewModel(
                addSale = { null },
                addOrder = { null },
                basketRepository = BasketRepository(productRepository = { null }),
                get = { null },
                logger = DummyLogger(),
                navigator = DummyNavigator(),
                loading = UiText.Res(R.string.loading),
                failure = UiText.Res(R.string.failure),
            )
        }
    )
}