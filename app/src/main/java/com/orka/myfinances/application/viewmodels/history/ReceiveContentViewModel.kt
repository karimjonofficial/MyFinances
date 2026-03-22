package com.orka.myfinances.application.viewmodels.history

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApi1
import com.orka.myfinances.data.api.receive.map
import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.api.getChunk
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.screens.ChunkMapState
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveContentInteractor
import com.orka.myfinances.ui.screens.history.viewmodel.ReceiveUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class ReceiveContentViewModel(
    private val receiveApi: ReceiveApi1,
    events: Flow<ReceiveEvent>,
    loading: UiText,
    failure: UiText,
    formatPrice: FormatPrice,
    formatLocalDate: FormatLocalDate,
    formatTime: FormatTime,
    formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    logger: Logger
) : MapChunkViewModel<ReceiveApiModel, ReceiveUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> receiveApi.getChunk(size, page) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map =
            chunk.results.groupBy { receive -> receive.dateTime.toLocalDateTime(timeZone).date }
                .mapKeys { entry -> formatLocalDate.formatLocalDate(entry.key) }
                .mapValues { entry ->
                    entry.value.map { receive ->
                        receive.map(formatPrice, formatDecimal, formatTime)
                    }
                }

        ChunkMapState(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), ReceiveContentInteractor {
    override val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun select(receive: ReceiveUiModel) {
        launch { navigator.navigateToReceive(receive.id) }
    }
}