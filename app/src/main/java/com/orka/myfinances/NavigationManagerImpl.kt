package com.orka.myfinances

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
        val list = createBackStack()
        state.update { list }
    }

    override fun navigateToProfile() {
        if (!isDuplicate<Destination.Profile>()) {
            state.update {
                createBackStack(Destination.Profile)
            }
        }
    }

    override fun navigateToCatalog(catalog: Catalog) {
        val last = state.value.last()
        if (!(last is Destination.Catalog && last.catalog == catalog)) {
            val viewModel = provider.catalogViewModel(catalog)
            state.update { it + Destination.Catalog(catalog, viewModel) }
        }
    }

    override fun navigateToWarehouse(folder: Warehouse) {
        val last = state.value.last()
        if (!(last is Destination.Warehouse && last.warehouse == folder)) {
            state.update { it + Destination.Warehouse(folder, provider.warehouseViewModel(folder)) }
        }
    }

    override fun navigateToNotifications() {
        val backstack = createBackStack(Destination.Notifications)
        state.update { backstack }
    }

    override fun navigateToAddTemplate() {
        val viewModel = provider.addTemplateViewModel()
        val destination = Destination.AddTemplate(viewModel, types)
        state.update { createBackStack(destination) }
    }

    override fun navigateToAddProduct(warehouse: Warehouse) {
        val viewModel = provider.addProductViewModel()
        state.update { it + Destination.AddProduct(warehouse, viewModel) }
    }

    override fun navigateToSettings() {
        if (!isDuplicate<Destination.Settings>()) {
            state.update {
                createBackStack(Destination.Settings)
            }
        }
    }

    override fun navigateToTemplates() {
        if (!isDuplicate<Destination.Templates>()) {
            val templatesViewModel = provider.templatesViewModel()
            state.update { it + Destination.Templates(templatesViewModel) }
        }
    }

    override fun navigateToProduct(product: Product) {
        state.update { createBackStack(Destination.Product(product)) }
    }

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1) {
            val list = backstack.dropLast(1)
            state.update { list }
        }
    }

    private inline fun <reified T : Destination> isDuplicate(): Boolean = state.value.last() is T
    private fun createBackStack(vararg destinations: Destination = emptyArray()): List<Destination> {
        val home = state.value.first()
        val list = listOf(home) + destinations
        return list
    }
}