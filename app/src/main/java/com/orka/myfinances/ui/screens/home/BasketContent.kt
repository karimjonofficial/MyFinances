package com.orka.myfinances.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.orka.myfinances.fixtures.data.api.ProductApiServiceImpl
import com.orka.myfinances.lib.LoggerImpl
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.home.components.BasketItemCard
import com.orka.myfinances.ui.screens.home.viewmodel.BasketState
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Composable
fun BasketContent(
    modifier: Modifier = Modifier,
    state: BasketState,
    viewModel: BasketContentViewModel
) {
    when (state) {
        is BasketState.Loading -> {
            LoadingScreen(modifier)
        }

        is BasketState.Success -> {
            Column(modifier = modifier) {
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    Text(text = stringResource(R.string.items))

                    VerticalSpacer(4)
                    LazyColumn(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (state.items.isNotEmpty()) {
                            items(items = state.items) {
                                BasketItemCard(
                                    item = it,
                                    imageRes = R.drawable.furniture,
                                    increase = { item -> viewModel.increase(item.product.id) },
                                    decrease = { item -> viewModel.decrease(item.product.id) },
                                    remove = { _ -> /**viewModel.remove(item)**/ }
                                )
                            }
                        }
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
                        text = "$"+state.price.toString(),
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Button(onClick = { }) {
                        Text(text = stringResource(R.string.sell))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BasketContentPreview() {
    val logger = LoggerImpl()
    val apiService = ProductApiServiceImpl()
    val productRepository = ProductRepository(apiService)
    val basketRepository = BasketRepository(productRepository)
    val viewModel =
        BasketContentViewModel(basketRepository, logger, CoroutineScope(Dispatchers.Default))
    viewModel.initialize()
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Basket") }
            )
        },
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
                    },
                )
            }
        }
    ) { innerPadding ->
        BasketContent(
            modifier = Modifier.padding(innerPadding),
            state = uiState.value,
            viewModel = viewModel
        )
    }
}

