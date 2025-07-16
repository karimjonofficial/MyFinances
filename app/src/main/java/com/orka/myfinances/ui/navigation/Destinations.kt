package com.orka.myfinances.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Destinations {
    @Serializable
    data object Home : Destinations

    @Serializable
    data class Category(val id: Int) : Destinations

    @Serializable
    data object Profile : Destinations
}