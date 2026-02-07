package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.sale.SaleScreen

fun saleEntry(
    modifier: Modifier = Modifier,
    navigator: Navigator,
    destination: Destination.Sale
) : NavEntry<Destination> = entry(destination) {
    SaleScreen(modifier, navigator, destination.sale)
}