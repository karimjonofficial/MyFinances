package com.orka.myfinances.ui.navigation.destination

import com.orka.myfinances.data.models.Id

sealed interface ProductTitleDestinations : Destination {
    data class List(val id: Id) : ProductTitleDestinations
    data class Add(val id: Id) : ProductTitleDestinations
    data class Edit(val id: Id) : ProductTitleDestinations
}