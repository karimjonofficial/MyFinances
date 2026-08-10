package com.orka.myfinances.ui.navigation.entries.order

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.OrderDestinations
import com.orka.myfinances.ui.screens.order.list.incompleted.OrdersScreen

fun ordersEntry(
    modifier: Modifier,
    destination: OrderDestinations.List,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "orders_${session.branchId.value}",
        initializer = { factory.ordersViewModel() }
    )
    val state = viewModel.uiState.collectAsState()

    OrdersScreen(
        modifier = modifier,
        state = state.value,
        interactor = viewModel
    )
}