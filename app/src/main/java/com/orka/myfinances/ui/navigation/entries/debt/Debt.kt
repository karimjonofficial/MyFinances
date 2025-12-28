package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.debt.DebtDetailScreen

fun debtEntry(
    modifier: Modifier = Modifier.Companion,
    destination: Destination.Debt,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    DebtDetailScreen(
        modifier = modifier,
        debt = destination.debt,
        onBackClick = { navigationManager.back() },
        onEditClick = {},
        onMarkAsPaidClick = {}
    )
}