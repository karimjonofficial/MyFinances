package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.factories.viewmodel.Factory
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.clients.ClientsScreen

fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = entry(destination) {

    ClientsScreen(
        modifier = modifier,
        viewModel = viewModel { factory.clientsViewModel() },
        navigator = navigator
    )
}