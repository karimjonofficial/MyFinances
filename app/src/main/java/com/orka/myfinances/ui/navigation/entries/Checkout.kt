package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.checkout.CheckoutScreen

fun checkoutEntry(
    modifier: Modifier = Modifier,
    destination: Destination.Checkout,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.checkoutViewModel() }
    val state = viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    CheckoutScreen(
        modifier = modifier,
        state = state.value,
        viewModel = viewModel
    )
}