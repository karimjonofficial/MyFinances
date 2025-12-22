package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.sale.SaleRepository
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.fixtures.managers.DummyNavigationManager
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.resources.models.basket.basketItems
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.data.repositories.order.OrderRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.data.api.ProductApiServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3ExpressiveApi::class
)
@Composable
fun CheckoutScreen(
    modifier: Modifier,
    viewModel: CheckoutScreenViewModel,
    navigationManager: NavigationManager,
    items: List<BasketItem>
) {
    val uiState = viewModel.uiState.collectAsState()

    when (val state = uiState.value) {
        is State.Initial, is State.Loading -> {
            Scaffold(
                modifier = modifier,
                topBar = { TopAppBar(title = { Text(text = stringResource(R.string.checkout)) }) }
            ) { paddingValues ->
                LoadingScreen(modifier = Modifier.scaffoldPadding(paddingValues))
            }
        }
        is State.Failure -> {
            Scaffold(
                modifier = modifier,
                topBar = { TopAppBar(title = { Text(text = stringResource(R.string.checkout)) }) }
            ) { paddingValues ->
                FailureScreen(modifier = Modifier.scaffoldPadding(paddingValues))
            }
        }

        is State.Success -> {
            CheckoutContent(
                modifier = modifier,
                items = items,
                clients = state.value,
                viewModel = viewModel,
                navigationManager = navigationManager
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
        navigationManager = DummyNavigationManager(),
        viewModel = CheckoutScreenViewModel(
            saleRepository = SaleRepository(),
            orderRepository = OrderRepository(),
            clientRepository = ClientRepository(),
            basketRepository = BasketRepository(ProductRepository(ProductApiServiceImpl())),
            coroutineScope = CoroutineScope(Dispatchers.Main),
            logger = DummyLogger()
        )
    )
}