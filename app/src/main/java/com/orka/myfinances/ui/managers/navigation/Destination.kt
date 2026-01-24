package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.Product

typealias CatalogModel = Catalog
typealias CategoryModel = Category
typealias ProductModel = Product
typealias ClientModel = Client
typealias OrderModel = Order
typealias DebtModel = Debt

sealed interface Destination {
    data object Home : Destination
    data class Catalog(val catalog: CatalogModel) : Destination
    data class Warehouse(val category: CategoryModel) : Destination
    data object Notifications : Destination
    data object Settings : Destination
    data object Templates : Destination
    data class Product(val product: ProductModel) : Destination
    data object Clients : Destination
    data class Client(val client: ClientModel) : Destination
    data class AddTemplate(val types: List<String>) : Destination
    data class AddProduct(val warehouse: CategoryModel) : Destination
    data object History : Destination
    data class Checkout(val items: List<BasketItem>) : Destination
    data class AddStockItem(val warehouse: CategoryModel) : Destination
    data object Orders : Destination
    data class Order(val order: OrderModel) : Destination
    data object Debts : Destination
    data class Debt(val debt: DebtModel) : Destination
    data object Search : Destination
}