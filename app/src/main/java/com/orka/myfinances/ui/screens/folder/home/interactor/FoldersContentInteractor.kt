package com.orka.myfinances.ui.screens.folder.home.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

interface FoldersContentInteractor : StateFul {
    fun addFolder(name: String, type: String, templateId: Id?)
    fun select(folder: FolderUiModel)
    fun navigateToNotifications()
    fun navigateToSearch()
    fun navigateToAddTemplate()

    companion object {
        val dummy = object : FoldersContentInteractor {
            override fun addFolder(name: String, type: String, templateId: Id?) {}
            override fun select(folder: FolderUiModel) {}
            override fun navigateToNotifications() {}
            override fun navigateToSearch() {}
            override fun navigateToAddTemplate() {}
            override fun initialize() {}
            override fun refresh() {}
        }
    }
}