package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.ui.components.HorizontalSpacer
import com.orka.myfinances.lib.ui.screens.StatefulScreen
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
    val price = rememberSaveable { mutableStateOf(if(state is State.Success) state.value.price else null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val printReceipt = rememberSaveable { mutableStateOf(if(state is State.Success) state.value.printerConnected else false) }
    val selectedClientId = retain { mutableStateOf(if(state is State.Success) state.value.clients.firstOrNull()?.id else null) }
    val selectedClient = if(state is State.Success) state.value.clients.find { it.id == selectedClientId.value } else null

    StatefulScreen(
        modifier = modifier,
        title = stringResource(R.string.checkout),
        bottomBar = {
            if(it is State.Success) {
                BottomAppBar(contentPadding = PaddingValues(horizontal = 16.dp)) {
                    val priceValue = price.value

                    Spacer(modifier = Modifier.weight(1f))
                    OutlinedButton(
                        enabled = selectedClient != null && priceValue != null,
                        onClick = {
                            interactor.order(
                                price = priceValue,
                                description = description.value,
                                id = selectedClientId.value!!
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
                                id = selectedClientId.value!!,
                                print = printReceipt.value
                            )
                        }
                    ) {
                        Text(text = stringResource(R.string.sell))
                    }
                }
            }
        },
        state = state,
        onRetry = interactor::refresh
    ) { modifier, state ->
        CheckoutContent(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
            items = state.items,
            clients = state.clients,
            price = state.price,
            selectedClient = selectedClient,
            printerConnected = state.printerConnected,
            description = description.value,
            printReceipt = printReceipt.value,
            onClientSelected = { selectedClientId.value = it },
            onPriceChanged = { price.value = it },
            onDescriptionChanged = { description.value = it },
            onPrintReceiptChanged = { printReceipt.value = it }
        )
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