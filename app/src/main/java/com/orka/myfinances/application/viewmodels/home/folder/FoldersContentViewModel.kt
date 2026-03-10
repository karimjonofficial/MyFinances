package com.orka.myfinances.application.viewmodels.home.folder

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.lib.viewmodel.DualStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersInteractor
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersState
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import kotlinx.coroutines.flow.asStateFlow

class FoldersContentViewModel(
    private val folderApi: FolderApi,
    private val templateApi: TemplateApi,
    private val office: Office,
    private val navigator: Navigator,
    logger: Logger
) : DualStateViewModel<FoldersState, TemplateState>(
    initialState1 = FoldersState.Initial,
    initialState2 = TemplateState.Initial,
    logger = logger
), FoldersInteractor {
    val uiState = state1.asStateFlow()
    val dialogState = state2.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val folders = folderApi.getByOffice(office.id.value)?.map { it.map() }
                if (folders != null) {
                    setState1(FoldersState.Success(folders.map { it.toUiModel() }))
                } else {
                    setState1(FoldersState.Error)
                }
            } catch (_: Exception) {
                setState1(FoldersState.Error)
            }
            try {
                val templates = templateApi.getAll()?.map { it.map() }
                if (templates != null) {
                    setState2(TemplateState.Success(templates))
                } else {
                    setState2(TemplateState.Error)
                }
            } catch (_: Exception) {
                setState2(TemplateState.Error)
            }
        }
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        launch {
            setState1(FoldersState.Loading)
            val request = AddFolderRequest(name, type, templateId, null)
            folderApi.add(request.map())

            val folders = folderApi.getByOffice(office.id.value)?.map { it.map() }
            if (folders != null) {
                setState1(FoldersState.Success(folders.map { it.toUiModel() }))
            } else {
                setState1(FoldersState.Error)
            }
        }
    }

    override fun select(folder: Folder) {
        launch {
            when (folder) {
                is Catalog -> navigator.navigateToCatalog(folder)
                is Category -> navigator.navigateToCategory(folder)
            }
        }
    }

    override fun navigateToNotifications() {
        launch {
            navigator.navigateToNotifications()
        }
    }

    override fun navigateToSearch() {
        launch {
            navigator.navigateToSearch()
        }
    }

    override fun navigateToAddTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }
}