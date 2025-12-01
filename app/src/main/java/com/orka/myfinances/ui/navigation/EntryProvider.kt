package com.orka.myfinances.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.R
import com.orka.myfinances.impl.ui.managers.NavigationManagerImpl
import com.orka.myfinances.lib.ui.entry.entry
import com.orka.myfinances.lib.ui.entry.lazyColumnEntry
import com.orka.myfinances.lib.ui.screens.FailureScreen
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.navigation.entries.add.addProductEntry
import com.orka.myfinances.ui.navigation.entries.add.addTemplateEntry
import com.orka.myfinances.ui.navigation.entries.basketEntry
import com.orka.myfinances.ui.navigation.entries.catalogEntry
import com.orka.myfinances.ui.navigation.entries.homeEntry
import com.orka.myfinances.ui.navigation.entries.notificationsEntry
import com.orka.myfinances.ui.navigation.entries.profileEntry
import com.orka.myfinances.ui.navigation.entries.settingsEntry
import com.orka.myfinances.ui.navigation.entries.templatesEntry
import com.orka.myfinances.ui.navigation.entries.warehouseEntry

fun entryProvider(
    modifier: Modifier,
    destination: Destination,
    navigationManager: NavigationManagerImpl
): NavEntry<Destination> = when (destination) {
    is Destination.Home -> homeEntry(modifier, destination, navigationManager)
    is Destination.Catalog -> catalogEntry(modifier, destination, navigationManager)
    is Destination.Warehouse -> warehouseEntry(modifier, destination, navigationManager)
    is Destination.Profile -> profileEntry(modifier, destination)
    is Destination.Notifications -> notificationsEntry(modifier, destination)
    is Destination.AddTemplate -> addTemplateEntry(modifier, destination, navigationManager)
    is Destination.Settings -> settingsEntry(modifier, destination, navigationManager)
    is Destination.Templates -> templatesEntry(modifier, destination)
    is Destination.AddProduct -> addProductEntry(modifier, destination, navigationManager)
    is Destination.Product -> entry(modifier, destination) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.product))
        }
    }

    is Destination.Basket -> basketEntry(destination, modifier)

    is Destination.Clients -> {
        if (destination.viewModel is ClientsScreenViewModel) {
            lazyColumnEntry(modifier, destination, destination.viewModel) {
                Text(text = it.name)
            }
        } else {
            entry(modifier, destination) {

                FailureScreen(
                    modifier = modifier,
                    message = stringResource(R.string.failure),
                    retry = {}
                )
            }
        }
    }
}