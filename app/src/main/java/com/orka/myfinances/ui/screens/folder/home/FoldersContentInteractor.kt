package com.orka.myfinances.ui.screens.folder.home

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.lib.ui.viewmodel.Searchable
import com.orka.myfinances.ui.models.ui.FolderUiModel

interface FoldersContentInteractor : Refreshable, Searchable {
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
            override fun refresh() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
        }
    }
}
