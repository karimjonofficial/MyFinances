package com.orka.myfinances.ui.navigation

import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

typealias FolderModel = com.orka.myfinances.data.models.folder.Folder

sealed interface Destination {

    @Serializable
    data class Home(
        @Contextual val viewModel: HomeScreenViewModel
    ) : Destination

    @Serializable
    data class Folder(
        @Contextual val folder: FolderModel
    ) : Destination

    @Serializable
    data object Profile : Destination
}