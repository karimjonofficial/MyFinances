package com.orka.myfinances.application.manager

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.fixtures.resources.Types
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Destination
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class NavigationManager(
    logger: Logger,
    initialBackStack: List<Destination> = listOf(Destination.Home),
) : SingleStateViewModel<List<Destination>>(
    initialState = initialBackStack,
    logger = logger
), Navigator {
    val backStack = state.asStateFlow()

    private fun navigate(destination: Destination) {
        updateState { state.value + destination }
    }

    override fun initialize() {}

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

    override fun navigateToCatalog(catalog: Catalog) {
        navigate(Destination.Catalog(catalog))
    }

    override fun navigateToCategory(category: Category) {
        navigate(Destination.Warehouse(category))
    }

    override fun navigateToNotifications() {
        navigate(Destination.Notifications)
    }

    override fun navigateToAddTemplate() {
        navigate(Destination.AddTemplate(Types.all))
    }

    override fun navigateToAddProduct(id: Id) {
        navigate(Destination.AddProduct(id))
    }

    override fun navigateToSettings() {
        navigate(Destination.Settings)
    }

    override fun navigateToTemplates() {
        navigate(Destination.Templates)
    }

    override fun navigateToProductTitle(id: Id) {
        navigate(Destination.ProductTitle(id))
    }

    override fun navigateToClients() {
        navigate(Destination.Clients)
    }

    override fun navigateToClient(id: Id) {
        navigate(Destination.Client(id))
    }

    override fun navigateToCheckout(items: List<BasketItem>) {
        navigate(Destination.Checkout(items))
    }

    override fun navigateToHistory() {
        navigate(Destination.History)
    }

    override fun navigateToAddStockItem(id: Id) {
        navigate(Destination.AddStockItem(id))
    }

    override fun navigateToOrders() {
        navigate(Destination.Orders)
    }

    override fun navigateToOrder(id: Id) {
        navigate(Destination.Order(id))
    }

    override fun navigateToDebts() {
        navigate(Destination.Debts)
    }

    override fun navigateToDebt(id: Id) {
        navigate(Destination.Debt(id))
    }

    override fun navigateToSearch() {
        navigate(Destination.Search)
    }

    override fun navigateToTemplate(id: Id) {
        navigate(Destination.Template(id))
    }

    override fun navigateToSale(id: Id) {
        navigate(Destination.Sale(id))
    }

    override fun navigateToReceive(id: Id) {
        navigate(Destination.Receive(id))
    }
}