package com.orka.myfinances.fixtures.managers

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.ui.navigation.Navigator

class DummyNavigator : Navigator {
    override fun navigateToHome() {}
    override fun navigateToCatalog(catalog: Catalog) {}
    override fun navigateToCategory(category: Category) {}
    override fun navigateToNotifications() {}
    override fun navigateToAddTemplate() {}
    override fun navigateToAddProduct(id: Id) {}
    override fun navigateToSettings() {}
    override fun navigateToTemplates() {}
    override fun navigateToProductTitle(id: Id) {}
    override fun navigateToClients() {}
    override fun navigateToClient(id: Id) {}
    override fun navigateToHistory() {}
    override fun navigateToCheckout(items: List<BasketItem>) {}
    override fun navigateToAddStockItem(id: Id) {}
    override fun navigateToOrders() {}
    override fun navigateToOrder(id: Id) {}
    override fun navigateToDebts() {}
    override fun navigateToDebt(id: Id) {}
    override fun navigateToSearch() {}
    override fun navigateToTemplate(id: Id) {}
    override fun navigateToSale(id: Id) {}
    override fun navigateToReceive(id: Id) {}
    override fun back() {}
}
