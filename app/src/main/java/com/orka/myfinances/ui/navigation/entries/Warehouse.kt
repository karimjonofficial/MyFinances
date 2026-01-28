package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.warehouse.WarehouseScreen

fun warehouseEntry(
    modifier: Modifier,
    destination: Destination.Warehouse,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = destination.category.id.value.toString(),
        initializer = { factory.warehouseViewModel(destination.category) }
    )

    WarehouseScreen(
        modifier = modifier,
        viewModel = viewModel,
        category = destination.category,
        navigator = navigator
    )
}