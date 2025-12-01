package com.orka.myfinances.ui.navigation.entries

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.screens.basket.BasketScreen
import com.orka.myfinances.ui.screens.basket.BasketScreenViewModel

fun basketEntry(
    destination: Destination.Basket,
    modifier: Modifier
): NavEntry<Destination> = entry(modifier, destination) {
    val viewModel = destination.viewModel as BasketScreenViewModel
    val uiState = viewModel.uiState.collectAsState()

    BasketScreen(
        state = uiState.value,
        viewModel = viewModel
    )
}