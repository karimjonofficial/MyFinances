package com.orka.myfinances.application.viewmodels.folder.catalog

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.folder.CatalogDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.FolderRepository
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
    private val repository: FolderRepository,
    loading: UiText,
    failure: UiText,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : BaseViewModel<CatalogScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val folders = repository.getByParent(catalogId)?.sortedBy { it.name }
        val catalog = repository.getById(catalogId)

        if (folders != null && catalog != null && catalog is CatalogDto) {
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
        tryTransition { oldState ->
            val request = validate(name, type, templateId)
                ?: return@tryTransition oldState

            val added = repository.add(request)
            if (added != null) {
                oldState
            } else State.Failure(failure, oldState.value)
        }
    }

    private fun validate(
        name: String,
        type: String,
        templateId: Id?
    ): AddFolderRequest? {
        val isValid = name.isNotBlank() && type.isNotBlank()

        return if (isValid) {
            AddFolderRequest(
                name = name,
                type = type,
                templateId = templateId,
                parentId = catalogId
            )
        } else null
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