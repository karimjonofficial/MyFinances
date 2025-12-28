package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.debt.DebtScreen
import com.orka.myfinances.ui.screens.debt.viewmodel.DebtScreenViewModel

fun debtsEntry(
    modifier: Modifier,
    destination: Destination.Debts,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {
    DebtScreen(
        modifier = modifier,
        viewModel = destination.viewModel as DebtScreenViewModel,
        navigationManager = navigationManager
    )
}
