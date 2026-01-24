package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.order.OrdersScreenViewModel
import com.orka.myfinances.ui.screens.order.OrdersScreen

fun ordersEntry(
    modifier: Modifier,
    destination: Destination.Orders,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {
    val viewModel = destination.viewModel as OrdersScreenViewModel
    OrdersScreen(modifier, viewModel, navigator)
}