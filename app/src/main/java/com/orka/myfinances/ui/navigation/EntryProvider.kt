package com.orka.myfinances.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.navigation.entries.catalogEntry
import com.orka.myfinances.ui.navigation.entries.categoryEntry
import com.orka.myfinances.ui.navigation.entries.checkoutEntry
import com.orka.myfinances.ui.navigation.entries.client.clientEntry
import com.orka.myfinances.ui.navigation.entries.client.clientsEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtsEntry
import com.orka.myfinances.ui.navigation.entries.historyEntry
import com.orka.myfinances.ui.navigation.entries.home.homeEntry
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

fun entryProvider(
    modifier: Modifier = Modifier,
    session: Session,
    destination: Destination,
    factory: Factory
): NavEntry<Destination> {
    return when(destination) {
        is Destination.Home -> homeEntry(modifier, destination, session, factory)
        is Destination.Catalog -> catalogEntry(modifier, destination, factory)
        is Destination.Category -> categoryEntry(modifier, destination, factory)
        is Destination.Notifications -> notificationsEntry(modifier, destination, factory)
        is Destination.AddTemplate -> addTemplateEntry(modifier, destination, factory)
        is Destination.Settings -> settingsEntry(modifier, destination)
        is Destination.Templates -> templatesEntry(modifier, destination, factory)
        is Destination.AddProduct -> addProductEntry(modifier, destination, factory)
        is Destination.ProductTitle -> productTitleEntry(modifier, destination, factory)
        is Destination.Clients -> clientsEntry(modifier, destination, factory)
        is Destination.Client -> clientEntry(modifier, destination, factory)
        is Destination.History -> historyEntry(modifier, destination, factory)
        is Destination.Checkout -> checkoutEntry(modifier, destination, factory)
        is Destination.AddStockItem -> addReceiveEntry(modifier, destination, factory)
        is Destination.Orders -> ordersEntry(modifier, destination, factory)
        is Destination.Order -> orderEntry(modifier, destination, factory)
        is Destination.Debts -> debtsEntry(modifier, destination, factory)
        is Destination.Debt -> debtEntry(modifier, destination, factory)
        is Destination.Search -> searchEntry(modifier, destination)
        is Destination.Template -> templateEntry(modifier, destination, factory)
        is Destination.Sale -> saleEntry(modifier, destination, factory)
        is Destination.Receive -> receiveEntry(modifier, factory, destination)
    }
}