package com.orka.myfinances.ui.managers.navigation

import com.orka.myfinances.data.models.folder.Folder
import kotlinx.coroutines.flow.StateFlow

interface NavigationManager {
    val backStack: StateFlow<List<Destination>>

    fun navigateToHome()
    fun navigateToProfile()
    fun navigateToFolder(folder: Folder)
    fun navigateToNotifications()
    fun navigateToAddTemplate()
    fun back()
}