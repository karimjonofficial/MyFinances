package com.orka.myfinances.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import com.orka.myfinances.data.models.Session
import com.orka.myfinances.factories.Factory
import com.orka.myfinances.ui.navigation.destination.ClientDestinations
import com.orka.myfinances.ui.navigation.destination.DebtDestinations
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.HomeSettings
import com.orka.myfinances.ui.navigation.destination.OrderDestinations
import com.orka.myfinances.ui.navigation.destination.ProductTitleDestinations
import com.orka.myfinances.ui.navigation.destination.SettingsDestinations
import com.orka.myfinances.ui.navigation.destination.TemplateDestinations
import com.orka.myfinances.ui.navigation.entries.catalogEntry
import com.orka.myfinances.ui.navigation.entries.categoryEntry
import com.orka.myfinances.ui.navigation.entries.checkoutEntry
import com.orka.myfinances.ui.navigation.entries.client.clientEntry
import com.orka.myfinances.ui.navigation.entries.client.clientsEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtEntry
import com.orka.myfinances.ui.navigation.entries.debt.debtsEntry
import com.orka.myfinances.ui.navigation.entries.defaults.category.selectDefaultCategoryEntry
import com.orka.myfinances.ui.navigation.entries.defaults.printer.defaultPrinterEntry
import com.orka.myfinances.ui.navigation.entries.historyEntry
import com.orka.myfinances.ui.navigation.entries.home.homeEntry
import com.orka.myfinances.ui.navigation.entries.notificationsEntry
import com.orka.myfinances.ui.navigation.entries.order.orderEntry
import com.orka.myfinances.ui.navigation.entries.order.ordersEntry
import com.orka.myfinances.ui.navigation.entries.product.addProductEntry
import com.orka.myfinances.ui.navigation.entries.product.editProductEntry
import com.orka.myfinances.ui.navigation.entries.product.productTitleEntry
import com.orka.myfinances.ui.navigation.entries.receive.addReceiveEntry
import com.orka.myfinances.ui.navigation.entries.receive.receiveEntry
import com.orka.myfinances.ui.navigation.entries.saleEntry
import com.orka.myfinances.ui.navigation.entries.searchEntry
import com.orka.myfinances.ui.navigation.entries.settings.printerEntry
import com.orka.myfinances.ui.navigation.entries.settings.settingsEntry
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
        is Destination.Catalog -> catalogEntry(modifier, session, destination, factory)
        is Destination.Category -> categoryEntry(modifier, destination, session, factory)
        is Destination.Notifications -> notificationsEntry(modifier, destination, session, factory)
        is Destination.History -> historyEntry(modifier, destination, session, factory)
        is Destination.Checkout -> checkoutEntry(modifier, destination, session, factory)
        is Destination.AddStockItem -> addReceiveEntry(modifier, destination, session, factory)
        is Destination.Search -> searchEntry(modifier, destination)
        is Destination.Sale -> saleEntry(modifier, destination, session, factory)
        is Destination.Receive -> receiveEntry(modifier, session, factory, destination)

        is TemplateDestinations -> {
            when(destination) {
                is TemplateDestinations.Add -> addTemplateEntry(modifier, destination, session, factory)
                is TemplateDestinations.List -> templatesEntry(modifier, destination, session, factory)
                is TemplateDestinations.Details -> templateEntry(modifier, destination, session, factory)
            }
        }

        is ProductTitleDestinations -> {
            when(destination) {
                is ProductTitleDestinations.Add -> addProductEntry(modifier, destination, session, factory)
                is ProductTitleDestinations.Edit -> editProductEntry(modifier, destination, session, factory)
                is ProductTitleDestinations.List -> productTitleEntry(modifier, destination, session, factory)
            }
        }

        is ClientDestinations -> {
            when(destination) {
                is ClientDestinations.List -> clientsEntry(modifier, destination, session, factory)
                is ClientDestinations.Details -> clientEntry(modifier, session, destination, factory)
            }
        }

        is OrderDestinations -> {
            when(destination) {
                is OrderDestinations.List -> ordersEntry(modifier, destination, session, factory)
                is OrderDestinations.Details -> orderEntry(modifier, destination, session, factory)
            }
        }

        is DebtDestinations -> {
            when(destination) {
                is DebtDestinations.List -> debtsEntry(modifier, destination, session, factory)
                is DebtDestinations.Details -> debtEntry(modifier, destination, session, factory)
            }
        }

        is SettingsDestinations -> {
            when(destination) {
                is HomeSettings -> {
                    when(destination) {
                        is HomeSettings.PinnedCategories -> pinnedCategoriesEntry(modifier, destination, session, factory)
                    }
                }

                is DefaultsSettings -> {
                    when(destination) {
                        is DefaultsSettings.Category -> selectDefaultCategoryEntry(modifier, destination, session, factory)
                        is DefaultsSettings.Printer -> defaultPrinterEntry(modifier, destination, session, factory)
                    }
                }

                is SettingsDestinations.Main -> settingsEntry(modifier, destination, session, factory)
                is SettingsDestinations.Printer -> printerEntry(modifier, destination, session, factory)
            }
        }
    }
}
