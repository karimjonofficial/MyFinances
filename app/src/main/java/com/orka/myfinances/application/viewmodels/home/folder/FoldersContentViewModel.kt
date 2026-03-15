package com.orka.myfinances.application.viewmodels.home.folder

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.viewmodel.DualStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.models.FolderUiModel
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersContentInteractor
import com.orka.myfinances.ui.screens.home.viewmodel.folder.FoldersState
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FoldersContentViewModel(
    private val folderApi: FolderApi,
    private val templateApi: TemplateApi,
    private val navigator: Navigator,
    events: Flow<FolderEvent>,
    logger: Logger
) : DualStateViewModel<FoldersState, TemplateState>(
    initialState1 = FoldersState.Initial,
    initialState2 = TemplateState.Initial,
    logger = logger
), FoldersContentInteractor {
    val uiState = state1.asStateFlow()
    val dialogState = state2.asStateFlow()

    init {
        events
            .onEach { if(it.catalogId == null) initialize() }
            .launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
                val folders = folderApi.getTop()?.map { it.map() }
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
            folderApi.add(request)
        }
    }

    override fun select(folder: FolderUiModel) {
        launch {
            when (folder.isCatalog) {
                true -> navigator.navigateToCatalog(folder.id)
                false -> navigator.navigateToCategory(folder.id)
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