package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.receive.ReceiveScreen

fun receiveEntry(
    modifier: Modifier = Modifier,
    factory: Factory,
    navigator: Navigator,
    destination: Destination.Receive
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = destination.receive.id.toString()) {
        factory.receiveViewModel(destination.receive)
    }
    val state = viewModel.uiState.collectAsState()

    ReceiveScreen(
        modifier = modifier,
        navigator = navigator,
        state = state.value
    )
}