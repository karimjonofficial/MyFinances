package com.orka.myfinances.application.viewmodels.folder.catalog

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.CatalogApiModel
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenModel
import com.orka.myfinances.ui.screens.folder.catalog.viewmodel.CatalogScreenInteractor
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import com.orka.myfinances.ui.screens.folder.home.state.TemplateState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatalogScreenViewModel(
    private val catalogId: Id,
    private val folderApi: FolderApi,
    private val templateApi: TemplateApi,
    loading: UiText,
    private val failure: UiText,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State<CatalogScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), CatalogScreenInteractor {
    private val _dialogState = MutableStateFlow<TemplateState>(TemplateState.Initial)
    override val dialogState = _dialogState.asStateFlow()
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach {
            if (it.catalogId == catalogId) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
                val folders = folderApi.getByParent(catalogId.value)
                val catalog = folderApi.getById(catalogId.value)

                if (folders != null && catalog != null && catalog is CatalogApiModel) {
                    setState(State.Success(catalog.toScreenModel(folders)))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun initDialog() {
        launch {
            try {
                val templates = templateApi.getAll()?.map { it.map() }
                if (templates != null) {
                    _dialogState.value = TemplateState.Success(templates)
                } else {
                    _dialogState.value = TemplateState.Error
                }
            } catch (_: Exception) {
                _dialogState.value = TemplateState.Error
            }
        }
    }

    override fun addFolder(
        name: String,
        type: String,
        templateId: Id?
    ) {
        launch {
            val request = AddFolderRequest(
                name = name,
                type = type,
                templateId = templateId,
                parentId = catalogId
            )
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

    override fun navigateToAddTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }
}