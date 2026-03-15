package com.orka.myfinances.ui.navigation.entries.order

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.order.details.OrderScreen

fun orderEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Order,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = destination.id.toString()) {
        factory.orderViewModel(destination.id)
    }
    val state = viewModel.uiState.collectAsState()

    OrderScreen(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}