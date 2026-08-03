package com.orka.myfinances.application.viewmodels.client.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.models.ui.ClientUiModel
import com.orka.myfinances.ui.screens.client.list.ClientsScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ClientsScreenViewModel(
    getChunk: GetChunk<ClientDto>,
    searchChunk: SearchChunk<ClientDto>,
    private val insert: Insert<AddClientRequest>,
    events: Flow<ClientEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SearchableMapChunkViewModel<ClientDto, ClientUiModel>(
    get = getChunk,
    searchRepository = searchChunk,
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.firstName }
            .groupBy { it.firstName.stickyHeaderKey() }
            .mapValues { it.value.map { client -> client.toUiModel() } }

        ChunkUiModel(
            size = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), ClientsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { refresh() }.launchIn(viewModelScope)
    }

    override fun add(
        name: String,
        lastName: String?,
        patronymic: String?,
        phone: String?,
        address: String?
    ) {
        launch {
            val request = AddClientRequest(
                firstName = name,
                lastName = lastName,
                patronymic = patronymic,
                phone = phone,
                address = address
            )
            insert.insert(request)
        }
    }

    override fun select(client: ClientUiModel) {
        launch {
            navigator.navigateToClient(client.id)
        }
    }
}
