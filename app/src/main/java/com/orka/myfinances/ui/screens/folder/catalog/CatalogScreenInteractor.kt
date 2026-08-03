package com.orka.myfinances.ui.screens.folder.catalog

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.lib.ui.viewmodel.Searchable
import com.orka.myfinances.ui.models.ui.FolderUiModel

interface CatalogScreenInteractor : Refreshable, Searchable {
    fun select(folder: FolderUiModel)
    fun addFolder(name: String, type: String, templateId: Id?)
    fun navigateToAddTemplate()

    companion object {
        val dummy = object : CatalogScreenInteractor {
            override fun select(folder: FolderUiModel) {}
            override fun addFolder(name: String, type: String, templateId: Id?) {}
            override fun navigateToAddTemplate() {}
            override fun refresh() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
        }
    }
}
