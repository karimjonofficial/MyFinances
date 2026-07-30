package com.orka.myfinances.application.viewmodels.select

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.data.repositories.PinnedCategoriesEvent
import com.orka.myfinances.application.data.repositories.PinnedCategoriesRepository
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.preferences.categories.AddPinnedCategoryRequest
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.list.format.FormatListViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.settings.home.PinnedCategoriesScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SelectedCategoriesViewModel(
    private val repository: PinnedCategoriesRepository,
    events: Flow<PinnedCategoriesEvent>,
    private val navigator: Navigator,
    logger: Logger
) : FormatListViewModel<Id, Int>(
    get = repository,
    map = { it.value },
    logger = logger
), PinnedCategoriesScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun save(ids: List<Int>) {
        tryTransition { oldState ->
            //TODO this is causing a glitch in the screen because the old list is changing mutable state in the screen using launched effect
            if (oldState is State.Success) {
                val ids = ids.toSet()
                val oldList = oldState.value.toSet()
                val list = ids - oldList
                val removed = oldList - ids

                list.forEach {
                    repository.add(AddPinnedCategoryRequest(Id(it)))
                }
                removed.forEach {
                    repository.remove(Id(it))
                }
                navigator.back()
                oldState
            } else State.Failure(failure, oldState.value)
        }
    }
}