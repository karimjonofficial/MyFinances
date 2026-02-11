package com.orka.myfinances.ui.navigation.entries.order

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.order.OrderScreen

fun orderEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Order,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {
    OrderScreen(
        modifier = modifier,
        order = destination.order,
        navigator = navigator
    )
}