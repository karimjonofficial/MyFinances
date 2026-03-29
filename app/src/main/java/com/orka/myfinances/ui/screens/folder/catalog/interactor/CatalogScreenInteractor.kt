package com.orka.myfinances.ui.screens.folder.catalog.interactor

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel

interface CatalogScreenInteractor : StateFul {
    fun select(folder: FolderUiModel)
    fun addFolder(name: String, type: String, templateId: Id?)
    fun navigateToAddTemplate()

    companion object {
        val dummy = object : CatalogScreenInteractor {
            override fun select(folder: FolderUiModel) {}
            override fun addFolder(name: String, type: String, templateId: Id?) {}
            override fun navigateToAddTemplate() {}
            override fun refresh() {}
        }
    }
}