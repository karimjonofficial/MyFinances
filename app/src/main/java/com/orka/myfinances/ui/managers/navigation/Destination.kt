package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Debt
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.order.Order
import com.orka.myfinances.data.models.product.Product

typealias CatalogModel = Catalog
typealias WarehouseModel = Category
typealias ProductModel = Product
typealias ClientModel = Client
typealias OrderModel = Order
typealias DebtModel = Debt

sealed interface Destination {
    data class Home(val foldersViewModel: Any, val basketViewModel: Any) : Destination
    data class Catalog(val catalog: CatalogModel, val viewModel: Any) : Destination
    data class Warehouse(val warehouse: WarehouseModel, val viewModel: Any) : Destination
    data class Notifications(val viewModel: Any) : Destination
    data object Settings : Destination
    data class Templates(val viewModel: Any) : Destination
    data class Product(val product: ProductModel) : Destination
    data class Clients(val viewModel: Any) : Destination
    data class Client(val client: ClientModel) : Destination
    data class AddTemplate(val viewModel: Any, val types: List<String>) : Destination
    data class AddProduct(val warehouse: WarehouseModel, val viewModel: Any) : Destination
    data class History(val saleViewModel: Any, val receiveViewModel: Any) : Destination
    data class Checkout(val items: List<BasketItem>, val viewModel: Any) : Destination
    data class AddStockItem(val warehouse: WarehouseModel, val viewModel: Any) : Destination
    data class Orders(val viewModel: Any) : Destination
    data class Order(val order: OrderModel) : Destination
    data class Debts(val viewModel: Any) : Destination
    data class Debt(val debt: DebtModel) : Destination
}