package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.MapperListViewModel
import com.orka.myfinances.lib.viewmodel.list.ListViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReceiveContentViewModel(
    events: Flow<ReceiveEvent>,
    repository: Get<Receive>,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<Receive, ReceiveUiModel>(
    repository = repository,
    loading = loading,
    failure = failure,
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<ReceiveUiModel> {
    override val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    fun select(receive: Receive) {
        launch { navigator.navigateToReceive(receive) }
    }
}