package com.orka.myfinances.ui.navigation

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias MFolder = com.orka.myfinances.data.models.folder.Folder

sealed interface Destinations {
    @Serializable
    data object Home : Destinations

    @Serializable
    data class Folder(@Contextual val folder: MFolder) : Destinations

    @Serializable
    data object Profile : Destinations
}