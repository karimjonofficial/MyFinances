package com.orka.myfinances.ui.navigation.entries.defaults.client

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.screens.settings.defaults.client.DefaultClientScreen

fun selectDefaultClientEntry(
    modifier: Modifier,
    destination: DefaultsSettings.Client,
    session: Session,
    factory: Factory
): NavEntry<Destination> = entry(destination) {
    val clientItemsViewModel = viewModel(
        key = "clientItemsViewModel_${session.branchId.value}",
        initializer = { factory.clientItemsViewModel() }
    )
    val defaultClientViewModel = viewModel(
        key = "defaultClientViewModel_${session.branchId.value}",
        initializer = { factory.selectDefaultClientViewModel() }
    )

    val clientsState = clientItemsViewModel.uiState.collectAsState()
    val selectedState = defaultClientViewModel.uiState.collectAsState()

    DefaultClientScreen(
        modifier = modifier,
        state = clientsState.value,
        selectedState = selectedState.value,
        interactor = defaultClientViewModel,
        loadMore = clientItemsViewModel::loadMore,
        refresh = {
            clientItemsViewModel.refresh()
            defaultClientViewModel.refresh()
        }
    )
}
