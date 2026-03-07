package com.orka.myfinances.ui.navigation

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.data.models.folder.Catalog as FolderCatalog
import com.orka.myfinances.data.models.folder.Category

sealed interface Destination {
    data object Home : Destination
    data class Catalog(val catalog: FolderCatalog) : Destination
    data class Warehouse(val category: Category) : Destination
    data object Notifications : Destination
    data object Settings : Destination
    data object Templates : Destination
    data class ProductTitle(val id: Id) : Destination
    data object Clients : Destination
    data class Client(val id: Id) : Destination
    data class AddTemplate(val types: List<String>) : Destination
    data class AddProduct(val id: Id) : Destination
    data object History : Destination
    data class Checkout(val items: List<BasketItem>) : Destination
    data class AddStockItem(val id: Id) : Destination
    data object Orders : Destination
    data class Order(val id: Id) : Destination
    data object Debts : Destination
    data class Debt(val id: Id) : Destination
    data object Search : Destination
    data class Template(val id: Id) : Destination
    data class Sale(val id: Id) : Destination
    data class Receive(val id: Id) : Destination
}