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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.home.components.BasketItemCard
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketState

@Composable
fun BasketContent(
    modifier: Modifier = Modifier,
    state: BasketState,
    viewModel: BasketContentViewModel
) {
    when (state) {
        is BasketState.Loading -> LoadingScreen(modifier)

        is BasketState.Success -> {
            Column(modifier = modifier) {
                Column(modifier = Modifier.weight(1f)) {
                    if (state.items.isNotEmpty()) {

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        ) {
                            item {
                                Text(text = stringResource(R.string.items))
                                VerticalSpacer(4)
                            }

                            items(items = state.items) {
                                BasketItemCard(
                                    item = it,
                                    imageRes = R.drawable.furniture,
                                    increase = { viewModel.increase(it.product.id) },
                                    decrease = { viewModel.decrease(it.product.id) },
                                    remove = { viewModel.remove(it) }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceContainer)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "$${state.price}",
                                style = MaterialTheme.typography.headlineMedium
                            )

                            Button(onClick = { viewModel.checkout() }) {
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
                                    modifier = Modifier.size(96.dp),
                                    painter = painterResource(R.drawable.shopping_cart_off),
                                    tint = MaterialTheme.colorScheme.error,
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
    val viewModel = viewModel {
        BasketContentViewModel(
            repository = BasketRepository(productRepository = { product1 }),
            navigator = DummyNavigator(),
            logger = DummyLogger()
        )
    }
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
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = uiState.value,
            viewModel = viewModel
        )
    }
}

