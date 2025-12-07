package com.orka.myfinances.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.User
import com.orka.myfinances.impl.ui.managers.NavigationManagerImpl
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.navigation.entries.add.addProductEntry
import com.orka.myfinances.ui.navigation.entries.add.addTemplateEntry
import com.orka.myfinances.ui.navigation.entries.catalogEntry
import com.orka.myfinances.ui.navigation.entries.clientEntry
import com.orka.myfinances.ui.navigation.entries.clientsEntry
import com.orka.myfinances.ui.navigation.entries.homeEntry
import com.orka.myfinances.ui.navigation.entries.notificationsEntry
import com.orka.myfinances.ui.navigation.entries.productEntry
import com.orka.myfinances.ui.navigation.entries.settingsEntry
import com.orka.myfinances.ui.navigation.entries.templatesEntry
import com.orka.myfinances.ui.navigation.entries.warehouseEntry

fun entryProvider(
    modifier: Modifier,
    user: User,
    destination: Destination,
    navigationManager: NavigationManagerImpl
): NavEntry<Destination> = when (destination) {
    is Destination.Home -> homeEntry(modifier, destination, user, navigationManager)
    is Destination.Catalog -> catalogEntry(modifier, destination, navigationManager)
    is Destination.Warehouse -> warehouseEntry(modifier, destination, navigationManager)
    is Destination.Notifications -> notificationsEntry(modifier, destination)
    is Destination.AddTemplate -> addTemplateEntry(modifier, destination, navigationManager)
    is Destination.Settings -> settingsEntry(modifier, destination)
    is Destination.Templates -> templatesEntry(modifier, destination, navigationManager)
    is Destination.AddProduct -> addProductEntry(modifier, destination, navigationManager)
    is Destination.Product -> productEntry(modifier, destination)
    is Destination.Clients -> clientsEntry(modifier, destination, navigationManager)
    is Destination.Client -> clientEntry(modifier, destination)
}