package com.orka.myfinances.ui.navigation.entries.debt

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.DebtScreen

fun debtEntry(
    modifier: Modifier = Modifier.Companion,
    destination: Destination.Debt,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {

    DebtScreen(
        modifier = modifier,
        debt = destination.debt,
        navigator = navigator
    )
}