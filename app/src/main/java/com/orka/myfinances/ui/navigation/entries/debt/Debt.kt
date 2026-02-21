package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.DebtScreen

fun debtEntry(
    modifier: Modifier = Modifier.Companion,
    destination: Destination.Debt,
    factory: Factory,
    navigator: Navigator,
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = destination.debt.id.toString()) {
        factory.debtViewModel(destination.debt)
    }
    val state = viewModel.uiState.collectAsState()

    DebtScreen(
        modifier = modifier,
        state = state.value,
        navigator = navigator
    )
}