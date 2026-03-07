package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.history.HistoryScreen

fun historyEntry(
    modifier: Modifier,
    destination: Destination.History,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val saleViewModel = viewModel { factory.salesViewModel() }
    val receiveViewModel = viewModel { factory.receivesViewModel() }

    HistoryScreen(
        modifier = modifier,
        saleViewModel = saleViewModel,
        receiveViewModel = receiveViewModel
    )
}