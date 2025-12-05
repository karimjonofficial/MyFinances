package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.warehouse.WarehouseScreen
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel

fun warehouseEntry(
    modifier: Modifier,
    destination: Destination.Warehouse,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {
    val viewModel = destination.viewModel as WarehouseScreenViewModel

    WarehouseScreen(
        modifier = modifier,
        viewModel = viewModel,
        warehouse = destination.warehouse,
        navigationManager = navigationManager
    )
}