package com.orka.myfinances.application.viewmodels.receive.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.list.ReceiveContentInteractor
import com.orka.myfinances.ui.models.ui.ReceiveUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ReceiveContentViewModel(
    getChunk: GetChunk<ReceiveDto>,
    searchChunk: SearchChunk<ReceiveDto>,
    events: Flow<ReceiveEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SearchableMapChunkViewModel<ReceiveDto, ReceiveUiModel>(
    get = getChunk,
    search = searchChunk,
    map = { it.toUiModel() },
    groupBy = { it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString() },
    logger = logger
), ReceiveContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { refresh() }.launchIn(viewModelScope)
    }

    override fun select(receive: ReceiveUiModel) {
        launch { navigator.navigateToReceive(receive.id) }
    }
}