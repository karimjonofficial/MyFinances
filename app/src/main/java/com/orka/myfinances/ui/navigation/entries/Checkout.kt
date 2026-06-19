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
import com.orka.myfinances.ui.models.ClientItemModel
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.checkout.CheckoutScreen
import com.orka.myfinances.ui.screens.client.list.AddClientDialog
import com.orka.myfinances.ui.screens.client.sheet.SelectClientBottomSheet
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

    val sheetVisible = rememberSaveable { mutableStateOf(false) }
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val sheetState = rememberBottomSheetState(initialValue = SheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    val selectedClient = retain { mutableStateOf<ClientItemModel?>(null) }

    CheckoutScreen(
        modifier = modifier,
        state = state.value,
        selectedClient = selectedClient.value,
        interactor = viewModel,
        onOpenClients = { sheetVisible.value = true },
        onOpenAddClient = { dialogVisible.value = true }
    )

    if (dialogVisible.value) {
        val dialogViewModel = viewModel(
            key = "dialog_${destination.index}",
            initializer = { factory.addClientViewModel() }
        )
        AddClientDialog(
            dismissRequest = { dialogVisible.value = false },
            onSuccess = dialogViewModel::add
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
            selectedClient = selectedClient.value,
            onSelected = {
                selectedClient.value = it

                coroutineScope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible)
                        sheetVisible.value = false
                }
            },
            onLoadMore = clientSheetViewModel::loadMore,
            onSearch = clientSheetViewModel::search
        )
    }
}
