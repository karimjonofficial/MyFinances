package com.orka.myfinances.fixtures.managers

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.ui.managers.navigation.NavigationManager

class DummyNavigationManager : NavigationManager {
    override fun navigateToHome() {}
    override fun navigateToCatalog(catalog: Catalog) {}
    override fun navigateToWarehouse(warehouse: Warehouse) {}
    override fun navigateToNotifications() {}
    override fun navigateToAddTemplate() {}
    override fun navigateToAddProduct(warehouse: Warehouse) {}
    override fun navigateToSettings() {}
    override fun navigateToTemplates() {}
    override fun navigateToProduct(product: Product) {}
    override fun navigateToClients() {}
    override fun navigateToClient(client: Client) {}
    override fun navigateToHistory() {}
    override fun navigateToCheckout(items: List<BasketItem>) {}
    override fun navigateToAddStockItem(warehouse: Warehouse) {}
    override fun navigateToOrders() {}
    override fun navigateToOrder(order: Order) {}
    override fun navigateToDebts() {}
    override fun navigateToDebt(debt: Debt) {}
    override fun back() {}
}