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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.format.FormatDecimalImpl
import com.orka.myfinances.fixtures.format.FormatPriceImpl
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.basket.basketItems
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.preview.ScaffoldPreview
import com.orka.myfinances.lib.ui.screens.LoadingScreen
import com.orka.myfinances.ui.screens.home.components.BasketItemCard
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.basket.BasketState
import com.orka.myfinances.ui.screens.home.viewmodel.basket.toUiModel

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
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {

                    if (state.items.isNotEmpty()) {
                        Text(
                            text = stringResource(R.string.items),
                            fontWeight = FontWeight.Bold
                        )

                        VerticalSpacer(4)
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(items = state.items) { uiModel ->
                                BasketItemCard(
                                    item = uiModel.model,
                                    increase = { viewModel.increase(uiModel.item) },
                                    decrease = { viewModel.decrease(uiModel.item) },
                                    remove = { viewModel.remove(uiModel.item) }
                                )
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
                                    tint = MaterialTheme.colorScheme.surfaceVariant,
                                    contentDescription = null
                                )

                                VerticalSpacer(4)
                                Text(text = stringResource(R.string.basket_is_empty))
                            }
                        }
                    }
                }

                if (state.items.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceContainer)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.total_price),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Text(
                                text = state.price,
                                color = MaterialTheme.colorScheme.secondary,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(onClick = { viewModel.checkout() }) {
                            Text(text = stringResource(R.string.checkout))
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
            repository = BasketRepository(getById = { null }),
            navigator = DummyNavigator(),
            formatPrice = { "" },
            formatDecimal = { "" },
            logger = DummyLogger()
        )
    }

    ScaffoldPreview(
        modifier = Modifier.fillMaxSize(),
        title = "Basket"
    ) { paddingValues ->
        BasketContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = BasketState.Success(
                items = basketItems.map { it.toUiModel(FormatPriceImpl(), FormatDecimalImpl()) },
                price = "100000.00 UZS"
            ),
            viewModel = viewModel
        )
    }
}
