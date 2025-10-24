package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse

interface NavigationManager {
    fun navigateToHome()
    fun navigateToProfile()
    fun navigateToCatalog(catalog: Catalog)
    fun navigateToProductFolder(folder: Warehouse)
    fun navigateToNotifications()
    fun navigateToAddTemplate()
    fun navigateToAddProduct(warehouse: Warehouse)
    fun navigateToSettings()
    fun navigateToTemplates()
    fun back()
}