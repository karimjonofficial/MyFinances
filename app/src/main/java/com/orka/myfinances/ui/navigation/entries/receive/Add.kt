package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.stock.AddReceiveScreen

fun addStockItemEntry(
    modifier: Modifier,
    destination: Destination.AddStockItem,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {

    AddReceiveScreen(
        modifier = modifier,
        category = destination.warehouse,
        products = products,
        viewModel = viewModel { factory.addReceiveViewModel() },
        navigator = navigator
    )
}