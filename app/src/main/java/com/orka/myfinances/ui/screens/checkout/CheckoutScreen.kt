package com.orka.myfinances.ui.screens.checkout

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.id1
import com.orka.myfinances.fixtures.resources.name
import com.orka.myfinances.format.LocalFormatter
import com.orka.myfinances.lib.ui.preview.DefaultPreview
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.printer.PrinterStatus
import com.orka.myfinances.ui.models.item.CheckoutItemModel
import com.orka.myfinances.ui.models.item.ClientItemModel
import com.orka.myfinances.ui.models.screen.CheckoutScreenModel
import com.orka.myfinances.ui.theme.MyFinancesTheme
import kotlin.time.Clock
import kotlin.time.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    interactor: CheckoutScreenInteractor,
    selectedClient: ClientItemModel?,
    state: State<CheckoutScreenModel>,
    printerStatus: PrinterStatus,
    onOpenClients: () -> Unit
) {
    val formatter = LocalFormatter.current
    val exposed = rememberSaveable { mutableStateOf(false) }
    val price = rememberSaveable { mutableStateOf<Int?>(null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val printReceipt = rememberSaveable { mutableStateOf(true) }

    val transactionType = rememberSaveable { mutableStateOf(TransactionType.SELL) }
    val paymentType = rememberSaveable { mutableStateOf(PaymentType.PAID) }

    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    val deliveryDate = rememberSaveable { mutableStateOf(today) }
    val dueDate = rememberSaveable { mutableStateOf(today) }

    val showDatePicker = remember { mutableStateOf(false) }
    val datePickerTarget = remember { mutableStateOf(DatePickerTarget.DELIVERY) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = when (datePickerTarget.value) {
            DatePickerTarget.DELIVERY -> deliveryDate.value
            DatePickerTarget.DUE -> dueDate.value
        }.toEpochMilliseconds()
    )

    LaunchedEffect(state) {
        if (state is State.Success && price.value == null) {
            price.value = state.value.exposedPrice
        }
    }

    if (showDatePicker.value) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker.value = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            when (datePickerTarget.value) {
                                DatePickerTarget.DELIVERY -> deliveryDate.value = date
                                DatePickerTarget.DUE -> dueDate.value = date
                            }
                        }
                        showDatePicker.value = false
                    }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker.value = false }) {
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
                    transactionType = transactionType.value,
                    paymentType = paymentType.value,
                    selectedClient = selectedClient,
                    onExecute = {
                        val clientId = selectedClient?.id
                        when {
                            transactionType.value == TransactionType.ORDER -> {
                                if (clientId != null) {
                                    interactor.order(
                                        clientId = clientId,
                                        price = price.value,
                                        description = description.value,
                                        endDate = deliveryDate.value
                                    )
                                }
                            }

                            paymentType.value == PaymentType.DEBT -> {
                                if (clientId != null) {
                                    interactor.debt(
                                        clientId = clientId,
                                        price = price.value,
                                        description = description.value,
                                        print = printReceipt.value,
                                        dueDate = dueDate.value
                                    )
                                }
                            }

                            else -> {
                                if (clientId != null) {
                                    interactor.sell(
                                        clientId = clientId,
                                        price = price.value,
                                        description = description.value,
                                        print = printReceipt.value
                                    )
                                }
                            }
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
            printerStatus = printerStatus,
            items = model.items,
            hiddenPrice = stringResource(R.string.uzs_f, formatter.formatNumber(model.salePrice)),
            selectedClient = selectedClient,
            exposed = exposed.value,
            price = price.value,
            description = description.value,
            printReceipt = printReceipt.value,
            transactionType = transactionType.value,
            paymentType = paymentType.value,
            deliveryDate = deliveryDate.value,
            dueDate = dueDate.value,
            onTransactionTypeChange = { transactionType.value = it },
            onPaymentTypeChange = { paymentType.value = it },
            onPriceChange = { price.value = it },
            onDescriptionChange = { description.value = it },
            onPrintReceiptChange = { printReceipt.value = !printReceipt.value },
            onOpenClients = onOpenClients,
            onPickDeliveryDate = {
                datePickerTarget.value = DatePickerTarget.DELIVERY
                showDatePicker.value = true
            },
            onPickDueDate = {
                datePickerTarget.value = DatePickerTarget.DUE
                showDatePicker.value = true
            }
        )
    }
}

private enum class DatePickerTarget { DELIVERY, DUE }

private fun LocalDate.toEpochMilliseconds(): Long {
    return atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

@DefaultPreview
@Composable
private fun CheckoutScreenPreview() {
    val model = CheckoutScreenModel(
        items = listOf(
            CheckoutItemModel("Product 1", 100000),
            CheckoutItemModel("Product 2", 200000)
        ),
        exposedPrice = 300000,
        salePrice = 300000
    )

    MyFinancesTheme {
        CheckoutScreen(
            interactor = CheckoutScreenInteractor.dummy,
            selectedClient = null,
            state = State.Success(model),
            printerStatus = PrinterStatus.Disconnected,
            onOpenClients = {}
        )
    }
}

@DefaultPreview
@Composable
private fun CheckoutScreenWithClientPreview() {
    val model = CheckoutScreenModel(
        items = listOf(
            CheckoutItemModel("Product 1", 100000),
            CheckoutItemModel("Product 2", 200000)
        ),
        exposedPrice = 300000,
        salePrice = 300000
    )
    val client = ClientItemModel(id = id1, title = name)

    MyFinancesTheme {
        CheckoutScreen(
            interactor = CheckoutScreenInteractor.dummy,
            selectedClient = client,
            state = State.Success(model),
            printerStatus = PrinterStatus.Disconnected,
            onOpenClients = {},
        )
    }
}
