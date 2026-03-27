package com.orka.myfinances.application.viewmodels.sale.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.sale.SaleApi1
import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.data.api.getChunk
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.sale.list.SaleContentInteractor
import com.orka.myfinances.ui.screens.sale.list.SaleUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SaleContentViewModel(
    private val saleApi: SaleApi1,
    events: Flow<SaleEvent>,
    loading: UiText,
    failure: UiText,
    priceFormatter: FormatPrice,
    formatLocalDate: FormatLocalDate,
    formatDateTime: FormatDateTime,
    private val navigator: Navigator,
    logger: Logger
) : MapChunkViewModel<SaleApiModel, SaleUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> saleApi.getChunk(size, page) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results.groupBy { sale -> sale.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { entry -> formatLocalDate.formatLocalDate(entry.key) }
            .mapValues { entry ->
                entry.value.map { sale -> sale.map(priceFormatter, formatDateTime) }
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
), SaleContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    override fun select(sale: SaleUiModel) {
        launch { navigator.navigateToSale(sale.id) }
    }
}