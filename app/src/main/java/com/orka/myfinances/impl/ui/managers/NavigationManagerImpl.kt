package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.fixtures.resources.types
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavigationManagerImpl(
    initialBackStack: List<Destination>,
    private val provider: ViewModelProvider,
    logger: Logger,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)
) : ViewModel<List<Destination>>(
    initialState = initialBackStack,
    logger = logger,
    coroutineScope = coroutineScope
), NavigationManager {
    val backStack = state.asStateFlow()

    override fun navigateToHome() {
        navigate(backStack.value[0])
    }

    override fun navigateToCatalog(catalog: Catalog) {
        val viewModel = provider.catalogViewModel(catalog)
        navigate(Destination.Catalog(catalog, viewModel))
    }

    override fun navigateToWarehouse(category: Category) {
        val viewModel = provider.warehouseViewModel(category)
        navigate(Destination.Warehouse(category, viewModel))
    }

    override fun navigateToNotifications() {
        navigate(Destination.Notifications(provider.notificationsViewModel()))
    }

    override fun navigateToAddTemplate() {
        val viewModel = provider.addTemplateViewModel()
        navigate(Destination.AddTemplate(viewModel, types))
    }

    override fun navigateToAddProduct(category: Category) {
        val viewModel = provider.addProductViewModel()
        navigate(Destination.AddProduct(category, viewModel))
    }

    override fun navigateToSettings() {
        navigate(Destination.Settings)
    }

    override fun navigateToTemplates() {
        val templatesViewModel = provider.templatesViewModel()
        navigate(Destination.Templates(templatesViewModel))
    }

    override fun navigateToProduct(product: Product) {
        navigate(Destination.Product(product))
    }

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1)
            state.update { backstack.dropLast(1) }
    }

    override fun navigateToClients() {
        val viewModel = provider.clientsViewModel()
        navigate(Destination.Clients(viewModel))
    }

    override fun navigateToClient(client: Client) {
        navigate(Destination.Client(client))
    }

    override fun navigateToCheckout(items: List<BasketItem>) {
        val viewModel = provider.checkoutViewModel()
        navigate(Destination.Checkout(items, viewModel))
    }

    override fun navigateToHistory() {
        val saleViewModel = provider.saleViewModel()
        val receiveViewModel = provider.receiveViewModel()
        navigate(Destination.History(saleViewModel, receiveViewModel))
    }

    override fun navigateToAddStockItem(category: Category) {
        val viewModel = provider.addStockItemViewModel()
        navigate(Destination.AddStockItem(category, viewModel))
    }

    override fun navigateToOrders() {
        val viewModel = provider.ordersViewModel()
        navigate(Destination.Orders(viewModel))
    }

    override fun navigateToOrder(order: Order) {
        navigate(Destination.Order(order))
    }

    override fun navigateToDebts() {
        navigate(Destination.Debts(provider.debtsViewModel()))
    }

    override fun navigateToDebt(debt: Debt) {
        navigate(Destination.Debt(debt))
    }

    private fun navigate(destination: Destination) {
        updateState { backStack.value + destination }
    }
}