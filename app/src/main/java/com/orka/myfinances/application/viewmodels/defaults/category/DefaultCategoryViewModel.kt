package com.orka.myfinances.application.viewmodels.defaults.category

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.defaults.SetDefaultCategory
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.defaults.category.SelectDefaultCategoryInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DefaultCategoryViewModel(
    private val getDefaultCategory: GetDefaultCategory,
    private val setDefaultCategory: SetDefaultCategory,
    flow: Flow<DefaultsEvent>,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleViewModel<Id?, Id?>(
    get = { getDefaultCategory.getDefaultCategoryId() },
    map = { it },
    logger = logger
), SelectDefaultCategoryInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        flow.onEach {
            if (it is DefaultsEvent.Category)
                initialize()
        }.launchIn(viewModelScope)
    }

    override fun select(id: Id) {
        tryTransition { _ ->
            setDefaultCategory.setDefaultCategoryId(id)
            navigator.back()
            State.Success(id)
        }
    }
}
