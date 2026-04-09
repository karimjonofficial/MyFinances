package com.orka.myfinances.application.viewmodels.folder.catalog

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.models.response.CatalogApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenInteractor
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenModel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatalogScreenViewModel(
    private val catalogId: Id,
    private val folderApi: FolderApi,
    loading: UiText,
    failure: UiText,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : BaseViewModel<CatalogScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val folders = folderApi.getByParent(catalogId.value)?.sortedBy { it.name }
        val catalog = folderApi.getById(catalogId.value)

        if (folders != null && catalog != null && catalog is CatalogApiModel) {
            State.Success(catalog.toScreenModel(folders))
        } else null
    },
    logger = logger
), CatalogScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach {
            if (it.catalogId == catalogId) initialize()
        }.launchIn(viewModelScope)
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