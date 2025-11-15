package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.product.Product

interface NavigationManager {
    fun navigateToHome()
    fun navigateToProfile()
    fun navigateToCatalog(catalog: Catalog)
    fun navigateToWarehouse(warehouse: Warehouse)
    fun navigateToNotifications()
    fun navigateToAddTemplate()
    fun navigateToAddProduct(warehouse: Warehouse)
    fun navigateToSettings()
    fun navigateToTemplates()
    fun navigateToProduct(product: Product)
    fun navigateToBasket()
    fun back()
}