package com.orka.myfinances.ui.navigation

import kotlinx.serialization.Serializable

interface CategoryDestinations {
    @Serializable
    data object Warehouse : CategoryDestinations

    @Serializable
    data object Products : CategoryDestinations
}