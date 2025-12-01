package com.orka.myfinances.fixtures

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.ui.managers.navigation.NavigationManager

class DummyNavigationManager : NavigationManager {
    override fun navigateToHome() {}
    override fun navigateToProfile() {}
    override fun navigateToCatalog(catalog: Catalog) {}
    override fun navigateToWarehouse(warehouse: Warehouse) {}
    override fun navigateToNotifications() {}
    override fun navigateToAddTemplate() {}
    override fun navigateToAddProduct(warehouse: Warehouse) {}
    override fun navigateToSettings() {}
    override fun navigateToTemplates() {}
    override fun navigateToProduct(product: Product) {}
    override fun navigateToBasket() {}
    override fun navigateToClients() {}
    override fun back() {}
}