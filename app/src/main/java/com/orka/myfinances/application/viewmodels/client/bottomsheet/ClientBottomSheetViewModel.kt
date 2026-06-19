package com.orka.myfinances.application.viewmodels.client.bottomsheet

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.client.details.toItemModel
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.lib.data.api.scoped.company.getChunk
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.models.ClientItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ClientBottomSheetViewModel(
    private val clientApi: ClientApi,
    events: Flow<ClientEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<ClientApiModel, ClientItemModel>(
    loading = loading,
    failure = failure,
    get = { size, page, query -> clientApi.getChunk(size, page, "first_name", query) },
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.firstName }
            .groupBy { it.firstName.stickyHeaderKey() }
            .mapValues { it.value.map { client -> client.toItemModel() } }

        ChunkUiModel(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), ClientBottomSheetInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { refresh() }.launchIn(viewModelScope)
    }
}
