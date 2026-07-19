package com.orka.myfinances.application.viewmodels.defaults.category

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.defaults.SetDefaultCategory
import com.orka.myfinances.data.repositories.folder.FolderRepository
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.defaults.category.SelectDefaultCategoryInteractor
import com.orka.myfinances.ui.screens.defaults.category.SelectDefaultCategoryScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SelectDefaultCategoryViewModel(
    private val foldersRepository: FolderRepository,
    private val getDefaultCategory: GetDefaultCategory,
    private val setDefaultCategory: SetDefaultCategory,
    flow: Flow<DefaultsEvent>,
    private val navigator: Navigator,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<SelectDefaultCategoryScreenModel>(
    produceInitialState = {
        val folders = foldersRepository.getAll(null)
        val defaultId = getDefaultCategory.getDefaultCategoryId()
        if (folders != null) {
            State.Success(SelectDefaultCategoryScreenModel(toItemModels(folders), defaultId))
        } else null
    },
    loading = loading,
    failure = failure,
    logger = logger
), SelectDefaultCategoryInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach {
            if(it is DefaultsEvent.Category)
                initialize()
        }.launchIn(viewModelScope)
    }

    override fun select(id: Id) {
        tryTransition { oldState ->
            setDefaultCategory.setDefaultCategoryId(id)
            val oldModel = oldState.value
            if (oldModel != null) {
                navigator.back()
                State.Success(oldModel.copy(defaultId = id))
            } else oldState
        }
    }
}
