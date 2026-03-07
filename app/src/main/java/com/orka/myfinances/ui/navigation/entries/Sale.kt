package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.sale.SaleScreen

fun saleEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Sale,
    factory: Factory
) : NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = destination.id.value.toString()) {
        factory.saleViewModel(destination.id)
    }
    val state = viewModel.uiState.collectAsState()

    SaleScreen(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}