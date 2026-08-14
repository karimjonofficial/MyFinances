package com.orka.myfinances.application.viewmodels.client.bottomsheet

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.client.details.toItemModel
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.item.ClientItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ClientBottomSheetViewModel(
    getChunk: GetChunk<ClientDto>,
    searchChunk: SearchChunk<ClientDto>,
    events: Flow<ClientEvent>,
    logger: Logger
) : SearchableMapChunkViewModel<ClientDto, ClientItemModel>(
    get = getChunk,
    search = searchChunk,
    map = { it.toItemModel() },
    groupBy = { it.firstName.stickyHeaderKey() },
    logger = logger
), ClientBottomSheetInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { refresh() }.launchIn(viewModelScope)
    }
}
