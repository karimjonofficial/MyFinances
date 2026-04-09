package com.orka.myfinances.application.viewmodels.folder.home

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.FormatListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FoldersContentViewModel(
    private val folderApi: FolderApi,
    private val navigator: Navigator,
    events: Flow<FolderEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : FormatListViewModel<FolderApiModel, FolderUiModel>(
    loading = loading,
    failure = failure,
    get = { folderApi.getTop() },
    map = { folder -> folder.toUiModel() },
    logger = logger
), FoldersContentInteractor {
    val uiState = state.asStateFlow()

    init {
        events.onEach { if(it.catalogId == null) initialize() }
            .launchIn(viewModelScope)
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        launch {
            setState(State.Loading(loading))
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
