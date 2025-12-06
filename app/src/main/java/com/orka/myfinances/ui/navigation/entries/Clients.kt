package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.clients.ClientCard
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients,
    navigationManager: NavigationManager
): NavEntry<Destination> = entry(destination) {

    LazyColumnScreen(
        modifier = modifier,
        viewModel = destination.viewModel as ClientsScreenViewModel,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.clients)) }
            )
        },
        item = { modifier, client ->
            ClientCard(
                modifier = modifier,
                client = client,
                onClick = { navigationManager.navigateToClient(it) }
            )
        }
    )
}