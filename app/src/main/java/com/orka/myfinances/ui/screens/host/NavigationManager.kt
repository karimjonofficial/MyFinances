package com.orka.myfinances.ui.screens.host

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.fixtures.resources.types
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.navigation.Destination
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationManager(
    initialBackStack: List<Destination>,
    logger: Logger
) : SingleStateViewModel<List<Destination>>(
    initialState = initialBackStack,
    logger = logger
), Navigator {
    val backStack = state.asStateFlow()

    private fun navigate(destination: Destination) {
        updateState { backStack.value + destination }
    }

    override fun initialize() {}

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1)
            state.update { backstack.dropLast(1) }
    }

    override fun navigateToHome() {
        navigate(backStack.value[0])
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
        navigate(Destination.AddTemplate(types))
    }

    override fun navigateToAddProduct(category: Category) {
        navigate(Destination.AddProduct(category))
    }

    override fun navigateToSettings() {
        navigate(Destination.Settings)
    }

    override fun navigateToTemplates() {
        navigate(Destination.Templates)
    }

    override fun navigateToProductTitle(productTitle: ProductTitle) {
        navigate(Destination.ProductTitle(productTitle))
    }

    override fun navigateToClients() {
        navigate(Destination.Clients)
    }

    override fun navigateToClient(client: Client) {
        navigate(Destination.Client(client))
    }

    override fun navigateToCheckout(items: List<BasketItem>) {
        navigate(Destination.Checkout(items))
    }

    override fun navigateToHistory() {
        navigate(Destination.History)
    }

    override fun navigateToAddStockItem(category: Category) {
        navigate(Destination.AddStockItem(category))
    }

    override fun navigateToOrders() {
        navigate(Destination.Orders)
    }

    override fun navigateToOrder(order: Order) {
        navigate(Destination.Order(order))
    }

    override fun navigateToDebts() {
        navigate(Destination.Debts)
    }

    override fun navigateToDebt(debt: Debt) {
        navigate(Destination.Debt(debt))
    }

    override fun navigateToSearch() {
        navigate(Destination.Search)
    }

    override fun navigateToTemplate(template: Template) {
        navigate(Destination.Template(template))
    }

    override fun navigateToSale(sale: Sale) {
        navigate(Destination.Sale(sale))
    }

    override fun navigateToReceive(receive: Receive) {
        navigate(Destination.Receive(receive))
    }
}