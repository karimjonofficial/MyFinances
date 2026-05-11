package com.orka.myfinances.ui.screens.checkout

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.application.viewmodels.client.bottomsheet.ClientBottomSheetViewModel
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenInteractor
import com.orka.myfinances.ui.screens.checkout.viewmodel.CheckoutScreenModel
import com.orka.myfinances.ui.screens.client.list.AddClientDialog
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CheckoutScreen(
    modifier: Modifier,
    interactor: CheckoutScreenInteractor,
    state: State<CheckoutScreenModel>,
    clientSheetViewModel: ClientBottomSheetViewModel
) {
    val price = rememberSaveable { mutableStateOf(if (state is State.Success) state.value.price else null) }
    val description = rememberSaveable { mutableStateOf<String?>(null) }
    val printReceipt = rememberSaveable { mutableStateOf(state is State.Success) }
    val selectedClient = retain { mutableStateOf<ClientItemModel?>(null) }

    val newClientFirstName = rememberSaveable { mutableStateOf<String?>(null) }
    val newClientLastName = rememberSaveable { mutableStateOf<String?>(null) }
    val newClientPatronymic = rememberSaveable { mutableStateOf<String?>(null) }
    val newClientPhone = rememberSaveable { mutableStateOf<String?>(null) }
    val newClientAddress = rememberSaveable { mutableStateOf<String?>(null) }

    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val addClientDialogVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    val clientSheetState = clientSheetViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        clientSheetViewModel.initialize()
    }

    LaunchedEffect(state) {
        if (state is State.Success) {
            if (price.value == null) price.value = state.value.price
            if (!printReceipt.value) printReceipt.value = true
        }
    }

    StatefulScreen(
        modifier = modifier,
        title = stringResource(R.string.checkout),
        bottomBar = {
            if (it is State.Success) {
                CheckoutScreenBottomBar(
                    price = price.value,
                    selectedClient = selectedClient.value,
                    newClientFirstName = newClientFirstName.value,
                    newClientLastName = newClientLastName.value,
                    newClientPatronymic = newClientPatronymic.value,
                    newClientPhone = newClientPhone.value,
                    newClientAddress = newClientAddress.value,
                    interactor = interactor,
                    description = description.value,
                    printReceipt = printReceipt.value
                )
            }
        },
        state = state,
        onRetry = interactor::refresh
    ) { modifier, model ->
        CheckoutContent(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp),
            items = model.items,
            price = price.value ?: 0,
            selectedClient = selectedClient.value,
            newClientFirstName = newClientFirstName.value,
            description = description.value,
            printReceipt = printReceipt.value,
            onOpenClients = { sheetVisible.value = true },
            onOpenAddClient = { addClientDialogVisible.value = true },
            onPriceChanged = { price.value = it },
            onDescriptionChanged = { description.value = it },
            onPrintReceiptChanged = { printReceipt.value = it }
        )
    }

    if (addClientDialogVisible.value) {
        AddClientDialog(
            dismissRequest = { addClientDialogVisible.value = false },
            onSuccess = { firstName, lastName, patronymic, phone, address ->
                newClientFirstName.value = firstName
                newClientLastName.value = lastName
                newClientPatronymic.value = patronymic
                newClientPhone.value = phone
                newClientAddress.value = address

                selectedClient.value = null
                addClientDialogVisible.value = false
            }
        )
    }

    if (sheetVisible.value) {
        SelectClientBottomSheet(
            state = clientSheetState.value,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            selectedClient = selectedClient.value,
            onSelected = {
                selectedClient.value = it
                newClientFirstName.value = null
                newClientLastName.value = null
                newClientPatronymic.value = null
                newClientPhone.value = null
                newClientAddress.value = null

                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        sheetVisible.value = false
                    }
                }
            },
            onLoadMore = clientSheetViewModel::loadMore
        )
    }
}
