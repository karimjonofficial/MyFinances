package com.orka.myfinances.ui.navigation.destination

import com.orka.myfinances.data.models.Id

sealed interface TemplateDestinations : Destination {
    data object List : TemplateDestinations
    data object Add : TemplateDestinations
    data class Details(val id: Id) : TemplateDestinations
}