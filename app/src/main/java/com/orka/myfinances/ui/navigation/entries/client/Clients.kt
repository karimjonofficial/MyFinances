package com.orka.myfinances.ui.navigation.entries.client

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.screens.clients.ClientsScreen

fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val viewModel = viewModel { factory.clientsViewModel() }
    val state = viewModel.uiState.collectAsState()

    ClientsScreen(
        modifier = modifier,
        viewModel = viewModel,
        state = state.value
    )
}