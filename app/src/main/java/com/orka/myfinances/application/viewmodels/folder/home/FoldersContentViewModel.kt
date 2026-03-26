package com.orka.myfinances.application.viewmodels.folder.home

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.DualStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.state.FoldersContentModel
import com.orka.myfinances.ui.screens.folder.home.state.TemplateState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FoldersContentViewModel(
    private val folderApi: FolderApi,
    private val templateApi: TemplateApi,
    private val navigator: Navigator,
    events: Flow<FolderEvent>,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : DualStateViewModel<State<FoldersContentModel>, TemplateState>(
    initialState1 = State.Loading(loading),
    initialState2 = TemplateState.Initial,
    logger = logger
), FoldersContentInteractor {
    val uiState = state1.asStateFlow()
    val dialogState = state2.asStateFlow()

    init {
        initialize()
        events
            .onEach { if(it.catalogId == null) initialize() }
            .launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            setState1(State.Loading(loading))
            try {
                val folders = folderApi.getTop()
                if (folders != null) {
                    setState1(State.Success(FoldersContentModel(folders.map { it.toUiModel() })))
                } else setState1(State.Failure(failure))
            } catch (e: Exception) {
                setState1(State.Failure(UiText.Str(e.message.toString())))
            }
            try {
                val templates = templateApi.getAll()?.map { it.map() }
                if (templates != null) {
                    setState2(TemplateState.Success(templates))
                } else {
                    setState2(TemplateState.Error)
                }
            } catch (e: Exception) {
                setState2(TemplateState.Error)
                logger.log(
                    tag = "FoldersContentViewModel",
                    message = e.message.toString()
                )
            }
        }
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        launch {
            setState1(State.Loading(loading))
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
