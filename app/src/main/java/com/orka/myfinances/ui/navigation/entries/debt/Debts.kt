package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.DebtsScreen

fun debtsEntry(
    modifier: Modifier,
    destination: Destination.Debts,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    DebtsScreen(
        modifier = modifier,
        viewModel = viewModel { factory.debtsViewModel() },
        navigator = navigator
    )
}
