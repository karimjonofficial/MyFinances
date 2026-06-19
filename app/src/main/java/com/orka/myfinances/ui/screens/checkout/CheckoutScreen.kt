package com.orka.myfinances.ui.screens.checkout

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.models.ClientItemModel
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

private enum class CheckoutAction {
    ORDER, DEBT
}

@OptIn(ExperimentalMaterial3Api::class)
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

    var pendingAction by remember { mutableStateOf<CheckoutAction?>(null) }
    val datePickerState = rememberDatePickerState()

    LaunchedEffect(state) {
        if (state is State.Success && price.value == null) {
            price.value = state.value.exposedPrice
        }
    }

    if (pendingAction != null) {
        DatePickerDialog(
            onDismissRequest = { pendingAction = null },
            confirmButton = {
                TextButton(
                    onClick = {
                        val millis = datePickerState.selectedDateMillis
                        if (millis != null && selectedClient != null) {
                            val date = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date

                            when (pendingAction) {
                                CheckoutAction.ORDER -> interactor.order(
                                    clientId = selectedClient.id,
                                    price = price.value,
                                    description = description.value,
                                    endDate = date
                                )

                                CheckoutAction.DEBT -> interactor.debt(
                                    clientId = selectedClient.id,
                                    price = price.value,
                                    description = description.value,
                                    print = printReceipt.value,
                                    dueDate = date
                                )

                                else -> {}
                            }
                        }
                        pendingAction = null
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { pendingAction = null }) {
                    Text(text = stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

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
                    onOrderClick = { pendingAction = CheckoutAction.ORDER },
                    onDebtClick = { pendingAction = CheckoutAction.DEBT },
                    onSellClick = {
                        if (selectedClient != null) {
                            interactor.sell(
                                clientId = selectedClient.id,
                                price = price.value,
                                description = description.value,
                                print = printReceipt.value
                            )
                        }
                    }
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
