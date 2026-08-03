package com.orka.myfinances.ui.navigation.destination

import com.orka.myfinances.data.models.Id

sealed interface ClientDestinations : Destination {
    data object List : ClientDestinations
    data class Details(val id: Id) : ClientDestinations
}