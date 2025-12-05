package com.orka.myfinances.ui.navigation.entries

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.entry.lazyColumnEntry
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.clients.ClientsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun clientsEntry(
    modifier: Modifier,
    destination: Destination.Clients
): NavEntry<Destination> = lazyColumnEntry(
    modifier = modifier,
    destination = destination,
    viewModel = destination.viewModel as ClientsScreenViewModel,
    topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.clients)) }
        )
    },
    item = { Text(text = it.name) }
)