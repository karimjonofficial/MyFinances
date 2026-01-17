package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.fixtures.managers.DummyNavigationManager
import com.orka.myfinances.lib.LoggerImpl
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.home.components.BasketItemCard
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.BasketState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun BasketContent(
    modifier: Modifier = Modifier,
    state: BasketState,
    viewModel: BasketContentViewModel,
    navigationManager: NavigationManager
) {
    when (state) {
        is BasketState.Loading -> LoadingScreen(modifier)

        is BasketState.Success -> {
            Column(modifier = modifier) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    if (state.items.isNotEmpty()) {
                        Text(text = stringResource(R.string.items))

                        VerticalSpacer(4)
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ) {
                            items(items = state.items) {
                                BasketItemCard(
                                    item = it,
                                    imageRes = R.drawable.furniture,
                                    increase = { item -> viewModel.increase(item.product.id) },
                                    decrease = { item -> viewModel.decrease(item.product.id) },
                                    remove = { item -> viewModel.remove(item) }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$${state.price}",
                                style = MaterialTheme.typography.headlineMedium
                            )

                            Button(onClick = { navigationManager.navigateToCheckout(state.items) }) {
                                Text(text = stringResource(R.string.checkout))
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    modifier = Modifier.size(160.dp),
                                    painter = painterResource(R.drawable.shopping_cart_off),
                                    tint = MaterialTheme.colorScheme.surfaceTint,
                                    contentDescription = null
                                )

                                VerticalSpacer(4)
                                Text(text = stringResource(R.string.basket_is_empty))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun BasketContentPreview() {
    val logger = LoggerImpl()
    val productRepository = ProductRepository(ProductTitleRepository())
    val basketRepository = BasketRepository(productRepository)
    val viewModel = BasketContentViewModel(
        repository = basketRepository,
        logger = logger,
        coroutineScope = CoroutineScope(Dispatchers.Default)
    )
    viewModel.initialize()
    val uiState = viewModel.uiState.collectAsState()

    ScaffoldPreview(
        title = "Basket",
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.shopping_cart_filled),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    ) { paddingValues ->

        BasketContent(
            modifier = Modifier.padding(paddingValues),
            state = uiState.value,
            viewModel = viewModel,
            navigationManager = DummyNavigationManager()
        )
    }
}

