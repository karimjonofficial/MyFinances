package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.clients.ClientCard
import com.orka.myfinances.ui.screens.clients.ClientsScreen
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel

fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    ClientsScreen(
        modifier = modifier,
        destination = destination,
        navigationManager = navigationManager
    )
}