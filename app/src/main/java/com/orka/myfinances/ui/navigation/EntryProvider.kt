package com.orka.myfinances.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.managers.SessionManager
import com.orka.myfinances.ui.navigation.entries.catalogEntry
import com.orka.myfinances.ui.navigation.entries.checkoutEntry
import com.orka.myfinances.ui.navigation.entries.client.clientEntry
import com.orka.myfinances.ui.navigation.entries.client.clientsEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtsEntry
import com.orka.myfinances.ui.navigation.entries.historyEntry
import com.orka.myfinances.ui.navigation.entries.homeEntry
import com.orka.myfinances.ui.navigation.entries.notificationsEntry
import com.orka.myfinances.ui.navigation.entries.order.orderEntry
import com.orka.myfinances.ui.navigation.entries.order.ordersEntry
import com.orka.myfinances.ui.navigation.entries.product.addProductEntry
import com.orka.myfinances.ui.navigation.entries.product.productTitleEntry
import com.orka.myfinances.ui.navigation.entries.receive.addReceiveEntry
import com.orka.myfinances.ui.navigation.entries.receive.receiveEntry
import com.orka.myfinances.ui.navigation.entries.saleEntry
import com.orka.myfinances.ui.navigation.entries.searchEntry
import com.orka.myfinances.ui.navigation.entries.settingsEntry
import com.orka.myfinances.ui.navigation.entries.template.addTemplateEntry
import com.orka.myfinances.ui.navigation.entries.template.templateEntry
import com.orka.myfinances.ui.navigation.entries.template.templatesEntry
import com.orka.myfinances.ui.navigation.entries.warehouseEntry

fun entryProvider(
    modifier: Modifier = Modifier,
    session: Session,
    sessionManager: SessionManager,
    destination: Destination,
    navigator: Navigator,
    factory: Factory
): NavEntry<Destination> {
    return when(destination) {
        is Destination.Home -> homeEntry(modifier, destination, session, sessionManager, navigator, factory)
        is Destination.Catalog -> catalogEntry(modifier, destination, navigator, factory)
        is Destination.Warehouse -> warehouseEntry(modifier, destination, navigator, factory)
        is Destination.Notifications -> notificationsEntry(modifier, destination, factory)
        is Destination.AddTemplate -> addTemplateEntry(modifier, destination, navigator, factory)
        is Destination.Settings -> settingsEntry(modifier, destination)
        is Destination.Templates -> templatesEntry(modifier, destination, navigator, factory)
        is Destination.AddProduct -> addProductEntry(modifier, destination, navigator, factory)
        is Destination.ProductTitle -> productTitleEntry(modifier, destination, factory)
        is Destination.Clients -> clientsEntry(modifier, destination, navigator, factory)
        is Destination.Client -> clientEntry(modifier, destination)
        is Destination.History -> historyEntry(modifier, destination, navigator, factory)
        is Destination.Checkout -> checkoutEntry(modifier, destination, navigator, factory)
        is Destination.AddStockItem -> addReceiveEntry(modifier, destination, navigator, factory)
        is Destination.Orders -> ordersEntry(modifier, destination, navigator, factory)
        is Destination.Order -> orderEntry(modifier, destination)
        is Destination.Debts -> debtsEntry(modifier, destination, navigator, factory)
        is Destination.Debt -> debtEntry(modifier, destination, navigator)
        is Destination.Search -> searchEntry(modifier, destination)
        is Destination.Template -> templateEntry(modifier, destination)
        is Destination.Sale -> saleEntry(modifier, navigator, destination)
        is Destination.Receive -> receiveEntry(modifier, navigator, destination)
    }
}