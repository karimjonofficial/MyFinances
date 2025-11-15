package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.factories.viewmodel.ViewModelProvider
import com.orka.myfinances.fixtures.resources.types
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
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
    private val navState = MutableStateFlow(initialBackStack[0])
    val backStack = state.asStateFlow()
    val navigationState = navState.asStateFlow()

    override fun navigateToHome() {
        navigate(backStack.value[0])
        setNavState(backStack.value[0])
    }

    override fun navigateToProfile() {
        navigate(Destination.Profile)
        setNavState(Destination.Profile)
    }

    override fun navigateToCatalog(catalog: Catalog) {
        val viewModel = provider.catalogViewModel(catalog)
        navigate(Destination.Catalog(catalog, viewModel))
    }

    override fun navigateToWarehouse(warehouse: Warehouse) {
        val viewModel = provider.warehouseViewModel(warehouse)
        navigate(Destination.Warehouse(warehouse, viewModel))
    }

    override fun navigateToNotifications() {
        navigate(Destination.Notifications)
    }

    override fun navigateToAddTemplate() {
        val viewModel = provider.addTemplateViewModel()
        navigate(Destination.AddTemplate(viewModel, types))
    }

    override fun navigateToAddProduct(warehouse: Warehouse) {
        val viewModel = provider.addProductViewModel()
        navigate(Destination.AddProduct(warehouse, viewModel))
    }

    override fun navigateToSettings() {
        navigate(Destination.Settings)
        setNavState(Destination.Settings)
    }

    override fun navigateToTemplates() {
        val templatesViewModel = provider.templatesViewModel()
        navigate(Destination.Templates(templatesViewModel))
    }

    override fun navigateToProduct(product: Product) {
        navigate(Destination.Product(product))
    }

    override fun navigateToBasket() {
        val viewModel = provider.basketViewModel()
        val d = Destination.Basket(viewModel)
        navigate(d)
        setNavState(d)
    }

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1)
            state.update { backstack.dropLast(1) }
        navState.value = backstack[0]
    }

    private fun navigate(destination: Destination) {
        if(destination.hasNavBar)
            updateState { listOf(backStack.value[0], destination) }
        else updateState { backStack.value + destination }
    }
    private fun setNavState(destination: Destination) {
        navState.value = destination
    }
}