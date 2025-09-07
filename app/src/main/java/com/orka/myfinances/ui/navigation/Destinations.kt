package com.orka.myfinances.ui.navigation

import com.orka.myfinances.ui.managers.dialog.DialogState
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias FolderModel = com.orka.myfinances.data.models.folder.Folder

sealed interface Destinations {
    val dialogState: DialogState?

    @Serializable
    data class Home(
        override val dialogState: DialogState? = null,
        @Contextual val viewModel: HomeScreenViewModel
    ) : Destinations

    @Serializable
    data class Folder(
        @Contextual val folder: FolderModel,
        override val dialogState: DialogState? = null
    ) : Destinations

    @Serializable
    data class Profile(override val dialogState: DialogState? = null) : Destinations
}