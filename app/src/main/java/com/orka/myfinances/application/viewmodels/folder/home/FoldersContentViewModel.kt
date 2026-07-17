package com.orka.myfinances.application.viewmodels.folder.home

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.folder.toUiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetTop
import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesRepository
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentInteractor
import com.orka.myfinances.ui.screens.folder.home.interactor.FoldersContentModel
import com.orka.myfinances.ui.screens.folder.models.FolderUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class FoldersContentViewModel(
    private val getTop: GetTop,
    private val add: Add<Unit, AddFolderRequest>,
    private val pinnedCategoriesRepository: PinnedCategoriesRepository,
    private val navigator: Navigator,
    folderFlow: Flow<FolderEvent>,
    defaultsFlow: Flow<DefaultsEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<FoldersContentModel>(
    loading = loading,
    failure = failure,
    produceModel = {
        val folders = getTop.getTop(null)
        val categories = pinnedCategoriesRepository.getAll(null)

        if (folders != null) {
            FoldersContentModel(
                folders = folders.map { it.toUiModel() },
                pinnedCategories = categories
            )
        } else null
    },
    logger = logger
), FoldersContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        folderFlow.onEach {
            if (it.catalogId == null) initialize()
        }.launchIn(viewModelScope)

        defaultsFlow.onEach {
            if (it is DefaultsEvent.Category) initialize()
        }.launchIn(viewModelScope)
    }

    override fun addFolder(name: String, type: String, templateId: Id?) {
        launch {
            setState(State.Loading(loading))
            val request = AddFolderRequest(name, type, templateId, null)
            add.add(request)
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
