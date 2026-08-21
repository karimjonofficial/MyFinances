package com.orka.myfinances.application.viewmodels.select

import com.orka.myfinances.application.viewmodels.client.details.toItemModel
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.item.ClientItemModel
import kotlinx.coroutines.flow.asStateFlow

class ClientItemsViewModel(
    getChunk: GetChunk<ClientDto>,
    searchChunk: SearchChunk<ClientDto>,
    logger: Logger
) : SearchableMapChunkViewModel<ClientDto, ClientItemModel>(
    get = getChunk,
    search = searchChunk,
    map = { it.toItemModel() },
    groupBy = { it.firstName.take(1).uppercase() },
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }
}
