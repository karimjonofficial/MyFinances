package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.checkout.CheckoutContent
import com.orka.myfinances.ui.screens.checkout.CheckoutScreen
import com.orka.myfinances.ui.screens.checkout.CheckoutUIState
import com.orka.myfinances.ui.screens.checkout.SelectClientBottomSheet
import com.orka.myfinances.ui.screens.client.list.AddClientDialog
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun checkoutEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Checkout,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = destination.index.toString(),
        initializer = { factory.checkoutViewModel() }
    )
    val state = viewModel.uiState.collectAsState()
    val uiState = retain { CheckoutUIState() }

    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val addClientDialogVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    CheckoutScreen(
        modifier = modifier,
        state = state.value,
        uiState = uiState,
        interactor = viewModel
    ) { modifier, model ->
        CheckoutContent(
            modifier = modifier,
            items = model.items,
            hiddenPrice = model.salePrice,
            uiState = uiState,
            onOpenClients = { sheetVisible.value = true },
            onOpenAddClient = { addClientDialogVisible.value = true }
        )
    }

    if (addClientDialogVisible.value) {
        AddClientDialog(
            dismissRequest = { addClientDialogVisible.value = false },
            onSuccess = { firstName, lastName, patronymic, phone, address ->
                uiState.newClientFirstName = firstName
                uiState.newClientLastName = lastName
                uiState.newClientPatronymic = patronymic
                uiState.newClientPhone = phone
                uiState.newClientAddress = address

                uiState.selectedClient = null
                addClientDialogVisible.value = false
            }
        )
    }

    if (sheetVisible.value) {
        val clientSheetViewModel = viewModel { factory.clientBottomSheetViewModel() }
        val clientSheetState = clientSheetViewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            clientSheetViewModel.initialize()
        }

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
            selectedClient = uiState.selectedClient,
            onSelected = {
                uiState.selectClient(it)

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
