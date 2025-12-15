package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.checkout.CheckoutScreen
import com.orka.myfinances.ui.screens.checkout.CheckoutScreenViewModel

fun checkoutEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Checkout,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {
    val viewModel = destination.viewModel as CheckoutScreenViewModel

    CheckoutScreen(
        modifier = modifier,
        items = destination.items,
        viewModel = viewModel,
        navigationManager = navigationManager
    )
}