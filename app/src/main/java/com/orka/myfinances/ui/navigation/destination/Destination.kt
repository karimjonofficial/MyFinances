package com.orka.myfinances.ui.navigation.destination

import com.orka.myfinances.data.models.Id

sealed interface Destination {
    data object Home : Destination
    data class Catalog(val id: Id) : Destination
    data class Category(val id: Id) : Destination
    data object Notifications : Destination
    data object History : Destination
    data class Checkout(val index: Int) : Destination
    data class AddStockItem(val id: Id) : Destination
    data object Search : Destination
    data class Sale(val id: Id) : Destination
    data class Receive(val id: Id) : Destination
}