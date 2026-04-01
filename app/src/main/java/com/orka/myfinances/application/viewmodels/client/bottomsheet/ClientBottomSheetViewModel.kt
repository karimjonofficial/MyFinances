package com.orka.myfinances.application.viewmodels.client.bottomsheet

import com.orka.myfinances.application.viewmodels.client.details.toItemModel
import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.lib.data.api.scoped.company.getChunk
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.screens.debt.list.ClientItemModel
import kotlinx.coroutines.flow.asStateFlow

class ClientBottomSheetViewModel(
    private val clientApi: ClientApi,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapChunkViewModel<ClientApiModel, ClientItemModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> clientApi.getChunk(size, page, "first_name") },
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.firstName }
            .groupBy { it.firstName.stickyHeaderKey() }
            .mapValues { it.value.map { client -> client.toItemModel() } }

        ChunkMapState(
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
}
