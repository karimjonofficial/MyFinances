package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.warehouse.WarehouseScreen

fun warehouseEntry(
    modifier: Modifier,
    destination: Destination.Warehouse,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    WarehouseScreen(
        modifier = modifier,
        categoryId = destination.id,
        factory = factory
    )
}
