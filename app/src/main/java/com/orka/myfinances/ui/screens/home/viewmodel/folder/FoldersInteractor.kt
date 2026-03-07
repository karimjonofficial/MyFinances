package com.orka.myfinances.ui.screens.home.viewmodel.folder

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Folder

interface FoldersInteractor {
    fun addFolder(name: String, type: String, templateId: Id?)
    fun select(folder: Folder)
    fun navigateToNotifications()
    fun navigateToSearch()
    fun navigateToAddTemplate()
}