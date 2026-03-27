package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.debt.list.AddDebtDialog
import com.orka.myfinances.ui.screens.debt.list.DebtsScreen

fun debtsEntry(
    modifier: Modifier,
    destination: Destination.Debts,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.debtsViewModel() }
    val state = viewModel.uiState.collectAsState()
    val dialogState = viewModel.dialogState.collectAsState()
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    DebtsScreen(
        modifier = modifier,
        state = state.value,
        dialogVisible = dialogVisible.value,
        onAddDebt = { dialogVisible.value = true },
        interactor = viewModel
    ) {
        AddDebtDialog(
            state = dialogState.value,
            dismissRequest = { dialogVisible.value = false },
            onSuccess = { id, price, endDateTime, description ->
                viewModel.add(id, price, endDateTime, description)
            },
            onCancel = { dialogVisible.value = false }
        )
    }
}
