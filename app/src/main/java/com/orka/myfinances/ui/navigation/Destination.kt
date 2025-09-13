package com.orka.myfinances.ui.navigation

import com.orka.myfinances.ui.screens.home.HomeScreenViewModel

typealias FolderModel = com.orka.myfinances.data.models.folder.Folder

sealed interface Destination {

    data class Home(val viewModel: HomeScreenViewModel) : Destination
    data class Folder(val folder: FolderModel) : Destination
    data object Profile : Destination
    data object Notifications : Destination
}