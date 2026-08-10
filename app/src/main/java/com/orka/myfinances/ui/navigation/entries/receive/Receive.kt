package com.orka.myfinances.ui.navigation.entries.receive

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.receive.details.ReceiveScreen

fun receiveEntry(
    modifier: Modifier = Modifier,
    session: Session,
    factory: Factory,
    destination: Destination.Receive
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(key = "receive_${destination.id.value}_${session.branchId.value}") {
        factory.receiveViewModel(destination.id)
    }
    val state = viewModel.uiState.collectAsState()

    ReceiveScreen(
        modifier = modifier,
        interactor = viewModel,
        state = state.value
    )
}