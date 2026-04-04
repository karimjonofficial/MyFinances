package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
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
import com.orka.myfinances.ui.screens.debt.list.AddDebtDialog
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel
import com.orka.myfinances.ui.screens.debt.list.DebtsScreen
import com.orka.myfinances.ui.screens.checkout.SelectClientBottomSheet
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
fun debtsEntry(
    modifier: Modifier,
    destination: Destination.Debts,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.debtsViewModel() }
    val clientSheetViewModel = viewModel { factory.clientBottomSheetViewModel() }
    val state = viewModel.uiState.collectAsState()
    val clientSheetState = clientSheetViewModel.uiState.collectAsState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }
    val clientSheetVisible = rememberSaveable { mutableStateOf(false) }
    val selectedClient = retain { mutableStateOf<ClientItemModel?>(null) }
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    DebtsScreen(
        modifier = modifier,
        state = state.value,
        dialogVisible = dialogVisible.value,
        onAddDebt = { dialogVisible.value = true },
        interactor = viewModel
    ) {
        AddDebtDialog(
            selectedClient = selectedClient.value,
            onOpenClients = {
                clientSheetViewModel.initialize()
                clientSheetVisible.value = true
            },
            dismissRequest = {
                dialogVisible.value = false
                selectedClient.value = null
            },
            onSuccess = { id, price, endDateTime, description ->
                viewModel.add(id, price, endDateTime, description)
                dialogVisible.value = false
                selectedClient.value = null
            },
            onCancel = {
                dialogVisible.value = false
                selectedClient.value = null
            }
        )
    }

    if (clientSheetVisible.value) {
        SelectClientBottomSheet(
            state = clientSheetState.value,
            sheetState = sheetState,
            onDismissRequest = {
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        clientSheetVisible.value = false
                    }
                }
            },
            selectedClient = selectedClient.value,
            onSelected = {
                selectedClient.value = it
                coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        clientSheetVisible.value = false
                    }
                }
            },
            onLoadMore = clientSheetViewModel::loadMore
        )
    }
}
