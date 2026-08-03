package com.orka.myfinances.ui.navigation.destination

import com.orka.myfinances.data.models.Id

sealed interface OrderDestinations : Destination {
    data object List : OrderDestinations
    data class Details(val id: Id) : OrderDestinations
}