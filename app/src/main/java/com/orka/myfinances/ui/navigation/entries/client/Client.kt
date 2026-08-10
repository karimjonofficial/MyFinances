package com.orka.myfinances.ui.navigation.entries.client

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.ClientDestinations
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.client.details.ClientScreen

fun clientEntry(
    modifier: Modifier,
    session: Session,
    destination: ClientDestinations.Details,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "${destination.id.value}_${session.branchId.value}",
        initializer = { factory.clientViewModel(destination.id) }
    )
    val state = viewModel.uiState.collectAsState()
    ClientScreen(modifier, state.value, viewModel)
}
