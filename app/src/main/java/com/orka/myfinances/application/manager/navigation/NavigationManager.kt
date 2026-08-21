package com.orka.myfinances.application.manager.navigation

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.viewmodel.single.SingleStateViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.navigation.destination.ClientDestinations
import com.orka.myfinances.ui.navigation.destination.DebtDestinations
import com.orka.myfinances.ui.navigation.destination.DefaultsSettings
import com.orka.myfinances.ui.navigation.destination.Destination
import com.orka.myfinances.ui.navigation.destination.HomeSettings
import com.orka.myfinances.ui.navigation.destination.OrderDestinations
import com.orka.myfinances.ui.navigation.destination.ProductTitleDestinations
import com.orka.myfinances.ui.navigation.destination.SettingsDestinations
import com.orka.myfinances.ui.navigation.destination.TemplateDestinations
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager(
    logger: Logger,
    initialBackStack: List<Destination> = listOf(Destination.Home),
) : SingleStateViewModel<List<Destination>>(
    initialState = initialBackStack,
    logger = logger
), Navigator {
    val backStack = state.asStateFlow()
    private var checkoutIndex = 0

    private fun navigate(destination: Destination) {
        updateState { state.value + destination }
    }

    override fun back() {
        updateState {
            if (it.size > 1)
                it.dropLast(1)
            else it
        }
    }

    override fun navigateToHome() {
        navigate(state.value[0])
    }

    override fun navigateToCatalog(id: Id) {
        navigate(Destination.Catalog(id))
    }

    override fun navigateToCategory(id: Id) {
        navigate(Destination.Category(id))
    }

    override fun navigateToNotifications() {
        navigate(Destination.Notifications)
    }

    override fun navigateToAddTemplate() {
        navigate(TemplateDestinations.Add)
    }

    override fun navigateToAddProduct(id: Id) {
        navigate(ProductTitleDestinations.Add(id))
    }

    override fun navigateToEditProduct(id: Id) {
        navigate(ProductTitleDestinations.Edit(id))
    }

    override fun navigateToSettings() {
        navigate(SettingsDestinations.Main)
    }

    override fun navigateToTemplates() {
        navigate(TemplateDestinations.List)
    }

    override fun navigateToProductTitle(id: Id) {
        navigate(ProductTitleDestinations.List(id))
    }

    override fun navigateToClients() {
        navigate(ClientDestinations.List)
    }

    override fun navigateToClient(id: Id) {
        navigate(ClientDestinations.Details(id))
    }

    override fun navigateToCheckout() {
        navigate(Destination.Checkout(checkoutIndex++))
    }

    override fun navigateToHistory() {
        navigate(Destination.History)
    }

    override fun navigateToAddReceive(id: Id) {
        navigate(Destination.AddStockItem(id))
    }

    override fun navigateToOrders() {
        navigate(OrderDestinations.List)
    }

    override fun navigateToOrder(id: Id) {
        navigate(OrderDestinations.Details(id))
    }

    override fun navigateToDebts() {
        navigate(DebtDestinations.List)
    }

    override fun navigateToDebt(id: Id) {
        navigate(DebtDestinations.Details(id))
    }

    override fun navigateToSearch() {
        navigate(Destination.Search)
    }

    override fun navigateToTemplate(id: Id) {
        navigate(TemplateDestinations.Details(id))
    }

    override fun navigateToSale(id: Id) {
        navigate(Destination.Sale(id))
    }

    override fun navigateToReceive(id: Id) {
        navigate(Destination.Receive(id))
    }

    override fun navigateToSelectDefaultCategory() {
        navigate(DefaultsSettings.Category)
    }

    override fun navigateToSelectDefaultTemplate() {
        navigate(DefaultsSettings.Template)
    }

    override fun navigateToSelectDefaultClient() {
        navigate(DefaultsSettings.Client)
    }

    override fun navigateToPinnedCategories() {
        navigate(HomeSettings.PinnedCategories)
    }

    override fun navigateToPrinters() {
        navigate(SettingsDestinations.Printer)
    }

    override fun navigateToDefaultPrinter() {
        navigate(DefaultsSettings.Printer)
    }
}