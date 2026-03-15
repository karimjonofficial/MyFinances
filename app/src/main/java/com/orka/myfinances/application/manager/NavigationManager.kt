package com.orka.myfinances.application.manager

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
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

    private fun navigateTopLevel(destination: Destination) {
        updateState { 
            val root = it.firstOrNull() ?: Destination.Home
            if (destination == root) listOf(root)
            else listOf(root, destination)
        }
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
        navigateTopLevel(Destination.Home)
    }

    override fun navigateToCatalog(id: Id) {
        navigate(Destination.Catalog(id))
    }

    override fun navigateToCategory(id: Id) {
        navigate(Destination.Warehouse(id))
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
        navigateTopLevel(Destination.Settings)
    }

    override fun navigateToTemplates() {
        navigate(Destination.Templates)
    }

    override fun navigateToProductTitle(id: Id) {
        navigate(Destination.ProductTitle(id))
    }

    override fun navigateToClients() {
        navigateTopLevel(Destination.Clients)
    }

    override fun navigateToClient(id: Id) {
        navigate(Destination.Client(id))
    }

    override fun navigateToCheckout(items: List<BasketItem>) {
        navigate(Destination.Checkout(items))
    }

    override fun navigateToHistory() {
        navigateTopLevel(Destination.History)
    }

    override fun navigateToAddReceive(id: Id) {
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

    override fun navigateToBasket() {
        navigateTopLevel(Destination.Basket)
    }

    override fun navigateToProfile() {
        navigateTopLevel(Destination.Profile)
    }
}