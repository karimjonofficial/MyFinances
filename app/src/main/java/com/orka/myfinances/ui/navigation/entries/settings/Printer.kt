package com.orka.myfinances.ui.navigation.entries.settings

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.SettingsDestinations
import com.orka.myfinances.ui.screens.settings.printers.PrintersScreen

fun printerEntry(
    modifier: Modifier,
    destination: SettingsDestinations.Printer,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel {
        factory.bluetoothPrintersViewModel()
    }
    val state = viewModel.uiState.collectAsState()
    val printer = factory.printerManager()
    val printerStatus = printer.status.collectAsState()

    PrintersScreen(
        modifier = modifier,
        state = state.value,
        printerStatus = printerStatus.value,
        interactor = viewModel
    )
}