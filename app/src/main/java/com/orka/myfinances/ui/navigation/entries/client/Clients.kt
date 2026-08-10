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
import com.orka.myfinances.ui.screens.client.list.ClientsScreen

fun clientsEntry(
    modifier: Modifier,
    destination: ClientDestinations.List,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel(
        key = "clients_${session.branchId.value}",
        initializer = { factory.clientsViewModel() }
    )
    val state = viewModel.uiState.collectAsState()

    ClientsScreen(
        modifier = modifier,
        interactor = viewModel,
        state = state.value
    )
}