package com.orka.myfinances.ui.screens.checkout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.models.ClientItemModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    interactor: CheckoutScreenInteractor,
    selectedClient: ClientItemModel?,
    state: State<CheckoutScreenModel>,
    onOpenClients: () -> Unit,
    onOpenAddClient: () -> Unit
) {
    val exposed = rememberSaveable { mutableStateOf(false) }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val printReceipt = rememberSaveable { mutableStateOf(true) }

    StatefulScreen(
        modifier = modifier,
        topBar = {
            CheckoutScreenTopBar(
                exposed = exposed.value,
                onExposedChange = { exposed.value = !exposed.value }
            )
        },
        bottomBar = {
            if (it is State.Success) {
                CheckoutScreenBottomBar(
                    selectedClient = selectedClient,
                    price = price.value,
                    description = description.value,
                    printReceipt = printReceipt.value,
                    interactor = interactor
                )
            }
        },
        state = state,
        onRetry = interactor::refresh
    ) { modifier, model ->
        CheckoutContent(
            modifier = modifier,
            items = model.items,
            hiddenPrice = model.salePrice,
            selectedClient = selectedClient,
            exposed = exposed.value,
            price = price.value,
            description = description.value,
            printReceipt = printReceipt.value,
            onPriceChange = { price.value = it },
            onDescriptionChange = { description.value = it },
            onPrintReceiptChange = { printReceipt.value = !printReceipt.value },
            onOpenClients = onOpenClients,
            onOpenAddClient = onOpenAddClient
        )
    }
}
