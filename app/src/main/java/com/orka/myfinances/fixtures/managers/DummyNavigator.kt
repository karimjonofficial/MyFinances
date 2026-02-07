package com.orka.myfinances.fixtures.managers

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.ui.navigation.Navigator

class DummyNavigator : Navigator {
    override fun navigateToHome() {}
    override fun navigateToCatalog(catalog: Catalog) {}
    override fun navigateToCategory(category: Category) {}
    override fun navigateToNotifications() {}
    override fun navigateToAddTemplate() {}
    override fun navigateToAddProduct(category: Category) {}
    override fun navigateToSettings() {}
    override fun navigateToTemplates() {}
    override fun navigateToProductTitle(productTitle: ProductTitle) {}
    override fun navigateToClients() {}
    override fun navigateToClient(client: Client) {}
    override fun navigateToHistory() {}
    override fun navigateToCheckout(items: List<BasketItem>) {}
    override fun navigateToAddStockItem(category: Category) {}
    override fun navigateToOrders() {}
    override fun navigateToOrder(order: Order) {}
    override fun navigateToDebts() {}
    override fun navigateToDebt(debt: Debt) {}
    override fun navigateToSearch() {}
    override fun navigateToTemplate(template: Template) {}
    override fun navigateToSale(sale: Sale) {}
    override fun navigateToReceive(receive: Receive) {}
    override fun back() {}
}