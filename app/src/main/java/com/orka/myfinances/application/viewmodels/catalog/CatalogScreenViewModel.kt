package com.orka.myfinances.application.viewmodels.catalog

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.R
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
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenInteractor
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenModel
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import com.orka.myfinances.application.viewmodels.home.folder.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatalogScreenViewModel(
    private val catalog: Catalog,
    private val folderApi: FolderApi,
    private val templateApi: TemplateApi,
    private val office: Office,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), CatalogScreenInteractor {
    private val _dialogState = MutableStateFlow<TemplateState>(TemplateState.Initial)
    val dialogState = _dialogState.asStateFlow()
    val uiState = state.asStateFlow()

    init {
        events.onEach {
            if (it.catalogId == catalog.id) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            try {
                val folders = folderApi.getByParent(
                    parentId = catalog.id.value,
                    officeId = office.id.value
                )?.map { it.map() }

                if (folders != null) {
                    setState(
                        State.Success(
                            value = CatalogScreenModel(
                                catalog = catalog,
                                folders = folders.map { it.toUiModel() }
                            )
                        )
                    )
                } else setState(State.Failure(UiText.Res(R.string.failure)))
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
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
                parentId = catalog.id
            )
            folderApi.add(request.map())
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

    override fun navigateToAddTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }
}