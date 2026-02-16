package com.orka.myfinances.ui.screens.catalog

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.folder.Folder
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetByParameter
import com.orka.myfinances.lib.ui.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.home.viewmodel.folder.TemplateState
import com.orka.myfinances.ui.screens.home.viewmodel.folder.toUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatalogScreenViewModel(
    private val catalog: Catalog,
    private val get: Get<Template>,
    private val getByParameter: GetByParameter<Folder, Catalog>,
    private val add: Add<Folder, AddFolderRequest>,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<CatalogScreenState>(
    initialState = CatalogScreenState.Loading,
    logger = logger
) {
    private val _dialogState = MutableStateFlow<TemplateState>(TemplateState.Initial)
    val dialogState = _dialogState.asStateFlow()
    val uiState = state.asStateFlow()

    init {
        events.onEach {
            if(it.catalogId == catalog.id) initialize()
        }.launchIn(viewModelScope)
    }

    override fun initialize() {
        launch {
            val folders = getByParameter.get(catalog)
            if (folders != null)
                setState(CatalogScreenState.Success(folders.map { it.toUiModel() }))
            else setState(CatalogScreenState.Failure)
        }
    }

    fun initDialog() {
        launch {
            val templates = get.get()
            if(templates != null)
                _dialogState.value = TemplateState.Success(templates)
            else _dialogState.value = TemplateState.Error
        }
    }

    fun addFolder(
        name: String,
        type: String,
        templateId: Id?
    ) {
        launch {
            add.add(AddFolderRequest(
                    name = name,
                    type = type,
                    templateId = templateId,
                    parentId = catalog.id
                ))
        }
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