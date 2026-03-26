package com.orka.myfinances.ui.screens.folder.home.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

interface FoldersContentInteractor {
    fun initialize()
    fun addFolder(name: String, type: String, templateId: Id?)
    fun select(folder: FolderUiModel)
    fun navigateToNotifications()
    fun navigateToSearch()
    fun navigateToAddTemplate()
}