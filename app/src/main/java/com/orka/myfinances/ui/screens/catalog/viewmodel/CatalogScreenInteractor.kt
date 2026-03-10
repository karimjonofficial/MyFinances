package com.orka.myfinances.ui.screens.catalog.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface CatalogScreenInteractor : StateFul {
    fun initDialog()
    fun select(folder: Folder)
    fun addFolder(name: String, type: String, templateId: Id?)
    fun navigateToAddTemplate()
}