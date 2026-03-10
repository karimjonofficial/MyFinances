package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.fixtures.resources.models.product.product1
import com.orka.myfinances.fixtures.resources.models.product.product2
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.lib.ui.components.DividedList
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.components.OutlinedCommentTextField
import com.orka.myfinances.lib.ui.components.OutlinedExposedDropDownTextField
import com.orka.myfinances.lib.ui.components.OutlinedIntegerTextField
import com.orka.myfinances.lib.ui.components.VerticalSpacer
import com.orka.myfinances.ui.screens.checkout.viewmodel.BasketItemCardModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.application.viewmodels.checkout.toModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun CheckoutContent(
    modifier: Modifier,
    items: List<BasketItemCardModel>,
    clients: List<Client>,
    price: Int,
    printerConnected: Boolean,
    interactor: CheckoutScreenInteractor
) {
    val price = rememberSaveable { mutableStateOf<Int?>(price) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val printReceipt = rememberSaveable { mutableStateOf(printerConnected) }
    val selectedClientId =
        rememberSaveable(clients) { mutableStateOf(clients.firstOrNull()?.id?.value) }
    val selectedClient = clients.find { it.id.value == selectedClientId.value }
    val menuExpanded = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        title = stringResource(R.string.checkout),
        bottomBar = {
            BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                val priceValue = price.value

                Spacer(modifier = Modifier.weight(1f))
                OutlinedButton(
                    enabled = selectedClient != null && priceValue != null,
                    onClick = {
                        interactor.order(
                            price = priceValue,
                            description = description.value,
                            client = selectedClient
                        )
                    }
                ) {
                    Text(text = stringResource(R.string.order))
                }

                HorizontalSpacer(8)
                Button(
                    enabled = selectedClient != null && priceValue != null,
                    onClick = {
                        interactor.sell(
                            price = priceValue,
                            description = description.value,
                            client = selectedClient,
                            print = printReceipt.value
                        )
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp)
        ) {
            DividedList(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.items_purchased),
                items = items,
                itemTitle = { it.title },
                itemSupportingText = { it.price }
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

            if (printerConnected) {
                VerticalSpacer(8)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            printReceipt.value = !printReceipt.value
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = printReceipt.value,
                        onCheckedChange = { printReceipt.value = it }
                    )
                    Text(text = stringResource(R.string.print_receipt))
                }
            }

            VerticalSpacer(8)
        }
    }
}

@Preview
@Composable
private fun CheckoutContentPreview() {
    val items = listOf(
        BasketItem(product1.map(), 1000),
        BasketItem(product2.map(), 10000)
    )

    MyFinancesTheme {
        CheckoutContent(
            modifier = Modifier.fillMaxSize(),
            items = items.map { it.toModel({ "" }, { "" }) },
            clients = clients,
            interactor = CheckoutScreenInteractor.dummy,
            price = 1000,
            printerConnected = true
        )
    }
}