package com.orka.myfinances.ui.navigation.entries.defaults.printer

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.settings.defaults.printer.DefaultPrinterScreen

fun defaultPrinterEntry(
    modifier: Modifier = Modifier,
    destination: DefaultsSettings.Printer,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val printersViewModel = viewModel(
        key = "printers_${session.branchId.value}",
        initializer = { factory.printersViewModel() }
    )
    val defaultPrinterViewModel = viewModel(
        key = "default_printer_${session.branchId.value}",
        initializer = { factory.defaultPrinterViewModel() }
    )
    val listState = printersViewModel.uiState.collectAsState()
    val screenState = defaultPrinterViewModel.uiState.collectAsState()

    DefaultPrinterScreen(
        modifier = modifier,
        listState = listState.value,
        screenState = screenState.value,
        onRetry = {
            printersViewModel.refresh()
            defaultPrinterViewModel.refresh()
        },
        onSave = { defaultPrinterViewModel.save(it.dto) }
    )
}