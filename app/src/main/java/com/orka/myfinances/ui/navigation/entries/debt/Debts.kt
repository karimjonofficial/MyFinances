package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.DebtsScreen
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel

fun debtsEntry(
    modifier: Modifier,
    destination: Destination.Debts,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {
    DebtsScreen(
        modifier = modifier,
        viewModel = destination.viewModel as DebtScreenViewModel,
        navigator = navigator
    )
}
