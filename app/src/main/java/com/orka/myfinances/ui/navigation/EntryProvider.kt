package com.orka.myfinances.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.User
import com.orka.myfinances.factories.viewmodel.Factory
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.Navigator
import com.orka.myfinances.ui.navigation.entries.catalogEntry
import com.orka.myfinances.ui.navigation.entries.checkoutEntry
import com.orka.myfinances.ui.navigation.entries.clientEntry
import com.orka.myfinances.ui.navigation.entries.clientsEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtsEntry
import com.orka.myfinances.ui.navigation.entries.historyEntry
import com.orka.myfinances.ui.navigation.entries.homeEntry
import com.orka.myfinances.ui.navigation.entries.notificationsEntry
import com.orka.myfinances.ui.navigation.entries.order.orderEntry
import com.orka.myfinances.ui.navigation.entries.ordersEntry
import com.orka.myfinances.ui.navigation.entries.product.addProductEntry
import com.orka.myfinances.ui.navigation.entries.product.productEntry
import com.orka.myfinances.ui.navigation.entries.receive.addStockItemEntry
import com.orka.myfinances.ui.navigation.entries.searchEntry
import com.orka.myfinances.ui.navigation.entries.settingsEntry
import com.orka.myfinances.ui.navigation.entries.template.addTemplateEntry
import com.orka.myfinances.ui.navigation.entries.template.templatesEntry
import com.orka.myfinances.ui.navigation.entries.warehouseEntry

fun entryProvider(
    modifier: Modifier = Modifier,
    user: User,
    destination: Destination,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> = when(destination) {
    is Destination.Home -> homeEntry(modifier, destination, user, navigator, factory)
    is Destination.Catalog -> catalogEntry(modifier, destination, navigator, factory)
    is Destination.Warehouse -> warehouseEntry(modifier, destination, navigator, factory)
    is Destination.Notifications -> notificationsEntry(modifier, destination, factory)
    is Destination.AddTemplate -> addTemplateEntry(modifier, destination, navigator, factory)
    is Destination.Settings -> settingsEntry(modifier, destination)
    is Destination.Templates -> templatesEntry(modifier, destination, navigator, factory)
    is Destination.AddProduct -> addProductEntry(modifier, destination, navigator, factory)
    is Destination.Product -> productEntry(modifier, destination)
    is Destination.Clients -> clientsEntry(modifier, destination, navigator, factory)
    is Destination.Client -> clientEntry(modifier, destination)
    is Destination.History -> historyEntry(modifier, destination, factory)
    is Destination.Checkout -> checkoutEntry(modifier, destination, navigator, factory)
    is Destination.AddStockItem -> addStockItemEntry(modifier, destination, navigator, factory)
    is Destination.Orders -> ordersEntry(modifier, destination, navigator, factory)
    is Destination.Order -> orderEntry(modifier, destination)
    is Destination.Debts -> debtsEntry(modifier, destination, navigator, factory)
    is Destination.Debt -> debtEntry(modifier, destination, navigator)
    is Destination.Search -> searchEntry(modifier, destination)
}