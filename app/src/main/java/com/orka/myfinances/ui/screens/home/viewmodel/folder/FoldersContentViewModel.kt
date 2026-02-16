package com.orka.myfinances.ui.screens.home.viewmodel.folder

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.viewmodel.DualStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class FoldersContentViewModel(
    private val get: Get<Folder>,
    private val add: Add<Folder, AddFolderRequest>,
    private val templateRepository: Get<Template>,
    private val navigator: Navigator,
    logger: Logger
) : DualStateViewModel<FoldersState, TemplateState>(
    initialState1 = FoldersState.Initial,
    initialState2 = TemplateState.Initial,
    logger = logger
) {
    val uiState = state1.asStateFlow()
    val dialogState = state2.asStateFlow()

    override fun initialize() {
        launch {
            setState1(FoldersState.Loading)
            val folders = get.get()
            if(folders != null)
                setState1(FoldersState.Success(folders.map { it.toUiModel() }))
            else setState1(FoldersState.Error)
            val templates = templateRepository.get()
            if(templates != null)
                setState2(TemplateState.Success(templates))
            else setState2(TemplateState.Error)
        }
    }

    fun addFolder(name: String, type: String, templateId: Id?) = launch {
        val previousState = state1.value
        setState1(FoldersState.Loading)
        val request = AddFolderRequest(name, type, templateId)
        val folder = add.add(request)
        if(folder != null) {
            val folders = get.get()
            setState1(FoldersState.Success(folders!!.map { it.toUiModel() }))
        }
        else setState1(previousState)
    }

    fun select(folder: Folder) {
        launch {
            when (folder) {
                is Catalog -> navigator.navigateToCatalog(folder)
                is Category -> navigator.navigateToCategory(folder)
            }
        }
    }
}