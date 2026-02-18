package com.orka.myfinances.ui.navigation.entries.order

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.order.OrdersScreen

fun ordersEntry(
    modifier: Modifier,
    destination: Destination.Orders,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.ordersViewModel() }
    val state = viewModel.uiState.collectAsState()

    OrdersScreen(
        modifier = modifier,
        state = state.value,
        viewModel = viewModel
    )
}