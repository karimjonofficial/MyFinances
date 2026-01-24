package com.orka.myfinances.ui.navigation.entries

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.screens.clients.ClientsScreen

fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients,
    navigator: Navigator
): NavEntry<Destination> = entry(destination) {

    ClientsScreen(
        modifier = modifier,
        destination = destination,
        navigator = navigator
    )
}