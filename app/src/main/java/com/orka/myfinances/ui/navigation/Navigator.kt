package com.orka.myfinances.ui.navigation

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category

interface Navigator {
    fun back()
    fun navigateToHome()
    fun navigateToCatalog(catalog: Catalog)
    fun navigateToCategory(category: Category)
    fun navigateToNotifications()
    fun navigateToAddTemplate()
    fun navigateToAddProduct(id: Id)
    fun navigateToSettings()
    fun navigateToTemplates()
    fun navigateToProductTitle(id: Id)
    fun navigateToClients()
    fun navigateToClient(id: Id)
    fun navigateToHistory()
    fun navigateToCheckout(items: List<BasketItem>)
    fun navigateToAddStockItem(id: Id)
    fun navigateToOrders()
    fun navigateToOrder(id: Id)
    fun navigateToDebts()
    fun navigateToDebt(id: Id)
    fun navigateToSearch()
    fun navigateToTemplate(id: Id)
    fun navigateToSale(id: Id)
    fun navigateToReceive(id: Id)
}