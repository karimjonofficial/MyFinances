package com.orka.myfinances.ui.managers

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

interface Navigator {
    fun back()
    fun navigateToHome()
    fun navigateToCatalog(catalog: Catalog)
    fun navigateToCategory(category: Category)
    fun navigateToNotifications()
    fun navigateToAddTemplate()
    fun navigateToAddProduct(category: Category)
    fun navigateToSettings()
    fun navigateToTemplates()
    fun navigateToProductTitle(productTitle: ProductTitle)
    fun navigateToClients()
    fun navigateToClient(client: Client)
    fun navigateToHistory()
    fun navigateToCheckout(items: List<BasketItem>)
    fun navigateToAddStockItem(category: Category)
    fun navigateToOrders()
    fun navigateToOrder(order: Order)
    fun navigateToDebts()
    fun navigateToDebt(debt: Debt)
    fun navigateToSearch()
    fun navigateToTemplate(template: Template)
    fun navigateToSale(sale: Sale)
    fun navigateToReceive(receive: Receive)
}