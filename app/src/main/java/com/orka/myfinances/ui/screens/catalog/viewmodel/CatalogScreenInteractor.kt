package com.orka.myfinances.ui.screens.catalog.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.DialogViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import com.orka.myfinances.ui.screens.home.models.FolderUiModel
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface CatalogScreenInteractor : StateFul, DialogViewModel<TemplateState> {
    fun initDialog()
    fun select(folder: FolderUiModel)
    fun addFolder(name: String, type: String, templateId: Id?)
    fun navigateToAddTemplate()

    companion object {
        val dummy = object : CatalogScreenInteractor {
            override val dialogState: StateFlow<TemplateState> = MutableStateFlow(TemplateState.Initial)
            override fun initDialog() {}
            override fun select(folder: FolderUiModel) {}
            override fun addFolder(name: String, type: String, templateId: Id?) {}
            override fun navigateToAddTemplate() {}
            override fun initialize() {}
        }
    }
}