package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.debt.DebtsScreen

fun debtsEntry(
    modifier: Modifier,
    destination: Destination.Debts,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.debtsViewModel() }
    val state = viewModel.uiState.collectAsState()

    DebtsScreen(
        modifier = modifier,
        state = state.value,
        viewModel = viewModel
    )
}
