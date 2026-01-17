package com.orka.myfinances.ui.navigation.entries.stock

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.fixtures.resources.models.product.products
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.stock.AddStockItemScreen
import com.orka.myfinances.ui.screens.stock.AddStockItemScreenViewModel

fun addStockItemEntry(
    modifier: Modifier,
    destination: Destination.AddStockItem,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    AddStockItemScreen(
        modifier = modifier,
        category = destination.warehouse,
        products = products,
        viewModel = destination.viewModel as AddStockItemScreenViewModel,
        navigationManager = navigationManager
    )
}