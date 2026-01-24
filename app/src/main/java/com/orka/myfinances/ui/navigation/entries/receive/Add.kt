package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.stock.AddReceiveScreen
import com.orka.myfinances.ui.screens.stock.AddReceiveScreenViewModel

fun addStockItemEntry(
    modifier: Modifier,
    destination: Destination.AddStockItem,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {

    AddReceiveScreen(
        modifier = modifier,
        category = destination.warehouse,
        products = products,
        viewModel = destination.viewModel as AddReceiveScreenViewModel,
        navigator = navigator
    )
}