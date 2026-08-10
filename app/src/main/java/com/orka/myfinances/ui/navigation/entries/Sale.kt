package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.sale.details.SaleScreen

fun saleEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Sale,
    session: Session,
    factory: Factory
) : NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "sale_${destination.id.value}_${session.branchId.value}",
        initializer = { factory.saleViewModel(destination.id) }
    )
    val state = viewModel.uiState.collectAsState()

    SaleScreen(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}