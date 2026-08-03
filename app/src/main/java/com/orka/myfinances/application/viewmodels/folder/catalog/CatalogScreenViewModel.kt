package com.orka.myfinances.application.viewmodels.folder.catalog

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.folder.CatalogDto
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetByParent
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.base.refreshable.RefreshableBaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.statuses.failure.CouldNotAdd
import com.orka.myfinances.ui.statuses.failure.IncorrectInput
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenInteractor
import kotlinx.coroutines.flow.MutableStateFlow
import com.orka.myfinances.ui.models.screen.CatalogScreenModel
import com.orka.myfinances.ui.models.ui.FolderUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class CatalogScreenViewModel(
    private val catalogId: Id,
    private val getByParent: GetByParent,
    private val getById: GetById<FolderDto>,
    private val add: Add<Unit, AddFolderRequest>,
    events: Flow<FolderEvent>,
    private val navigator: Navigator,
    logger: Logger
) : RefreshableBaseViewModel<CatalogScreenModel>(
    produceInitialState = {
        val folders = getByParent.getByParent(catalogId)?.sortedBy { it.name }
        val catalog = getById.getById(catalogId)

        if (folders != null && catalog != null && catalog is CatalogDto) {
            State.Success(catalog.toScreenModel(folders))
        } else State.Failure()
    },
    logger = logger
), CatalogScreenInteractor {
    val uiState = state.asStateFlow()
    private val cachedState = MutableStateFlow<CatalogScreenModel?>(null)

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

            if(request != null) {
                val added = add.add(request)
                if (added != null)
                    oldState
                else State.Failure(CouldNotAdd, oldState.value)
            } else State.Failure(IncorrectInput(emptyList()), oldState.value)
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

    override fun search(query: String) {
        tryTransition { oldState ->
            if (oldState is State.Success<CatalogScreenModel>) {
                if (query.isNotBlank()) {
                    if (cachedState.value == null) cachedState.value = oldState.value
                    val result = cachedState.value!!.folders.filter {
                        it.model.name.contains(query, ignoreCase = true)
                    }
                    State.Success(oldState.value.copy(folders = result))
                } else {
                    val cached = cachedState.value
                    cachedState.value = null
                    if (cached != null) State.Success(cached) else oldState
                }
            } else oldState
        }
    }

    override fun resetSearch() {
        tryTransition { oldState ->
            val cached = cachedState.value
            cachedState.value = null
            if (cached != null && oldState is State.Success<CatalogScreenModel>) {
                State.Success(cached)
            } else oldState
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
}