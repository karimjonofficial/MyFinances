package com.orka.myfinances.fixtures

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.managers.navigation.NavigationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DummyNavigationManager : NavigationManager {
    override val backStack: StateFlow<List<Destination>> = MutableStateFlow(emptyList())
    override fun navigateToHome() {}
    override fun navigateToProfile() {}
    override fun navigateToCatalog(catalog: Catalog) {}
    override fun navigateToProductFolder(folder: Warehouse) {}
    override fun navigateToNotifications() {}
    override fun navigateToAddTemplate() {}
    override fun navigateToAddProduct(warehouse: Warehouse) {}
    override fun navigateToSettings() {}
    override fun navigateToTemplates() {}
    override fun back() {}
}