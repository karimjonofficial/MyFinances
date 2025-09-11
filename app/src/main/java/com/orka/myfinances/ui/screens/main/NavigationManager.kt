package com.orka.myfinances.ui.screens.main

import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.ui.navigation.Destination
import kotlinx.coroutines.flow.StateFlow

interface NavigationManager {
    val backStack: StateFlow<List<Destination>>

    fun navigateToHome()
    fun navigateToProfile()
    fun navigateToFolder(folder: Folder)
    fun back()
}
