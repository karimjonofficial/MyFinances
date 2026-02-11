package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.Basket
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.fixtures.core.DummyLogger
import com.orka.myfinances.fixtures.managers.DummyNavigator
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2
import com.orka.myfinances.lib.extensions.models.getPrice
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun CheckoutContent(
    modifier: Modifier,
    items: List<BasketItem>,
    clients: List<Client>,
    viewModel: CheckoutScreenViewModel,
    navigator: Navigator
) {
    val price = rememberSaveable { mutableStateOf<Int?>(items.getPrice()) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val selectedClientId = rememberSaveable(clients) { mutableStateOf(clients.firstOrNull()?.id?.value) }
    val selectedClient = clients.find { it.id.value == selectedClientId.value }
    val menuExpanded = rememberSaveable { mutableStateOf(false) }
    val splitButtonMenuExpanded = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.checkout),
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    onClick = {
                        selectedClient?.let { client ->
                            price.value?.let { price ->
                                viewModel.order(
                                    basket = Basket(price, description.value, items),
                                    client = client
                                )
                                navigator.back()
                            }
                            splitButtonMenuExpanded.value = false
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.order))
                }

                HorizontalSpacer(8)
                Button(
                    onClick = {
                        selectedClient?.let { client ->
                            price.value?.let { price ->
                                viewModel.sell(
                                    basket = Basket(price, description.value, items),
                                    client = client
                                )
                                navigator.back()
                            }
                        }
                    }
                ) {
                    Text(text = stringResource(R.string.sell))
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = modifier
                .scaffoldPadding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
            DividedList(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.items_purchased),
                items = items,
                itemTitle = { it.product.title.name },
                itemSupportingText = {
                    val price = it.amount * it.product.title.defaultSalePrice
                    "$${it.product.title.defaultSalePrice} x ${it.amount}  = $$price"
                }
            )

            VerticalSpacer(16)
            Row {
                OutlinedExposedDropDownTextField(
                    modifier = Modifier.weight(1f),
                    text = selectedClient?.firstName ?: stringResource(R.string.clients),
                    label = stringResource(R.string.clients),
                    menuExpanded = menuExpanded.value,
                    onExpandChange = { menuExpanded.value = it },
                    onDismissRequested = { menuExpanded.value = false },
                    items = clients,
                    itemText = { it.firstName },
                    onItemSelected = { selectedClientId.value = it.id.value }
                )

                HorizontalSpacer(8)
                OutlinedIntegerTextField(
                    modifier = Modifier.width(160.dp),
                    label = stringResource(R.string.total_price),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.attach_money),
                            contentDescription = stringResource(R.string.price)
                        )
                    },
                    value = price.value,
                    onValueChange = { price.value = it }
                )
            }

            VerticalSpacer(8)
            OutlinedCommentTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description.value,
                onValueChange = { description.value = it }
            )

            VerticalSpacer(8)
        }
    }
}

@Preview
@Composable
private fun CheckoutContentPreview() {
    val items = listOf(
        BasketItem(product1, 1000),
        BasketItem(product2, 10000)
    )
    val viewModel = viewModel {
        CheckoutScreenViewModel(
            saleRepository = { null },
            orderRepository = { null },
            basketRepository = BasketRepository(productRepository = { null }),
            clientRepository = { null },
            logger = DummyLogger(),
            loading = UiText.Res(R.string.loading),
            failure = UiText.Res(R.string.failure)
        )
    }

    MyFinancesTheme {
        CheckoutContent(
            modifier = Modifier.fillMaxSize(),
            items = items,
            clients = clients,
            viewModel = viewModel,
            navigator = DummyNavigator(),
        )
    }
}
