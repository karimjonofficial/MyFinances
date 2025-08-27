package com.orka.myfinances.ui.navigation

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias MFolder = com.orka.myfinances.data.models.folder.Folder

sealed interface Destinations {
    val dialogState: DialogState?

    @Serializable
    data class Home(override val dialogState: DialogState? = null) : Destinations

    @Serializable
    data class Folder(
        @Contextual val folder: MFolder,
        override val dialogState: DialogState? = null
    ) : Destinations

    @Serializable
    data class Profile(override val dialogState: DialogState? = null) : Destinations
}