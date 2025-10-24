package com.orka.myfinances

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.factories.ViewModelProvider
import com.orka.myfinances.fixtures.resources.types
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.coroutines.CoroutineContext

class NavigationManagerImpl(
    initialBackStack: List<Destination>,
    private val provider: ViewModelProvider,
    logger: Logger,
    defaultCoroutineContext: CoroutineContext = Dispatchers.Default
) : ViewModel<List<Destination>>(
    initialState = initialBackStack,
    defaultCoroutineContext = defaultCoroutineContext,
    logger = logger
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
            state.update { it + Destination.Catalog(catalog) }
        }
    }

    override fun navigateToProductFolder(folder: Warehouse) {
        val last = state.value.last()
        if (!(last is Destination.Warehouse && last.warehouse == folder)) {
            state.update { it + Destination.Warehouse(folder, "viewModel") }//TODO
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
        if(!isDuplicate<Destination.Settings>()) {
            state.update {
                createBackStack(Destination.Settings)
            }
        }
    }

    override fun navigateToTemplates() {
        if(!isDuplicate<Destination.Templates>()) {
            val templatesViewModel = provider.templatesViewModel()
            if(templatesViewModel is TemplatesScreenViewModel)
                templatesViewModel.initialize()
            state.update { it + Destination.Templates(templatesViewModel) }
        }
    }

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1) {
            val list = backstack.dropLast(1)
            state.update { list }
        }
    }

    private inline fun <reified T: Destination> isDuplicate(): Boolean = state.value.last() is T
    private fun createBackStack(vararg destinations: Destination = emptyArray()): List<Destination> {
        val home = state.value.first()
        val list = listOf(home) + destinations
        return list
    }
}