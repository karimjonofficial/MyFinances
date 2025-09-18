package com.orka.myfinances

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.ViewModel
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.factories.ViewModelProvider
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
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
    override val backStack = state.asStateFlow()
    private val types = listOf("text", "number", "range")

    override fun navigateToHome() {
        val list = createBackStack()
        state.update { list }
    }

    override fun navigateToProfile() {
        if (state.value.last() !is Destination.Profile) {
            state.update {
                createBackStack(Destination.Profile)
            }
        }
    }

    override fun navigateToCatalog(catalog: Catalog) {
        val last = state.value.last()
        if (!(last is Destination.Catalog && last.catalog == catalog)) {
            val backstack = state.value
            val new = backstack + Destination.Catalog(catalog)
            state.update { new }
        }
    }

    override fun navigateToProductFolder(folder: ProductFolder) {
        val last = state.value.last()
        if (!(last is Destination.ProductFolder && last.productFolder == folder)) {
            val backstack = state.value
            val new = backstack + Destination.ProductFolder(folder)
            state.update { new }
        }
    }

    override fun navigateToNotifications() {
        val backstack = createBackStack(Destination.Notifications)
        state.update { backstack }
    }

    override fun back() {
        val backstack = state.value
        if (backstack.size > 1) {
            val list = backstack.dropLast(1)
            state.update { list }
        }
    }

    override fun navigateToAddTemplate() {
        val viewModel = provider.templateScreenViewModel()//TODO check code coverage
        val destination = Destination.AddTemplate(viewModel, types)
        val list = createBackStack(destination)
        state.update { list }
    }

    private fun createBackStack(vararg destinations: Destination = emptyArray()): List<Destination> {
        val home = state.value.first()
        val list = listOf(home) + destinations
        return list
    }
}