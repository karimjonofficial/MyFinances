package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ReceiveContentViewModel(
    events: Flow<ReceiveEvent>,
    repository: Get<Receive>,
    loading: Int,
    failure: Int,
    logger: Logger
) : ListViewModel<Int, Receive, Int>(
    repository = repository,
    loading = loading,
    failure = failure,
    logger = logger
) {
    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
    }
}