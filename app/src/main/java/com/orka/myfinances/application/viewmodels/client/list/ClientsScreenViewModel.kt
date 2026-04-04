package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.data.api.client.ClientApi
import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.api.client.toApiRequest
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.api.scoped.company.getChunk
import com.orka.myfinances.lib.data.api.scoped.company.insert
import com.orka.myfinances.lib.extensions.stickyHeaderKey
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientUiModel
import com.orka.myfinances.ui.screens.client.list.viewmodel.ClientsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class ClientsScreenViewModel(
    private val clientApi: ClientApi,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : MapChunkViewModel<ClientApiModel, ClientUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> clientApi.getChunk(size, page, "first_name") },
    map = { chunk ->
        val map = chunk.results
            .sortedBy { it.firstName }
            .groupBy { it.firstName.stickyHeaderKey() }
            .mapValues { it.value.map { client -> client.toUiModel() } }

        ChunkMapState(
            count = chunk.count,
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
            val created = clientApi.insert(
                request = request,
                map = AddClientRequest::toApiRequest
            )
            if (created) initialize()
        }
    }

    override fun select(client: ClientUiModel) {
        launch {
            navigator.navigateToClient(client.id)
        }
    }
}
