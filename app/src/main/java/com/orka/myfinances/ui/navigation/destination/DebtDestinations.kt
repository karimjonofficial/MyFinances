package com.orka.myfinances.ui.navigation.destination

import com.orka.myfinances.data.models.Id

sealed interface DebtDestinations : Destination {
    data object List : DebtDestinations
    data class Details(val id: Id) : DebtDestinations
}