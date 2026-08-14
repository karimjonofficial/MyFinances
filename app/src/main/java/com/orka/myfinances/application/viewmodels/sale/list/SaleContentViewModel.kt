package com.orka.myfinances.application.viewmodels.sale.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.sale.list.SaleContentInteractor
import com.orka.myfinances.ui.models.ui.SaleUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class SaleContentViewModel(
    getChunk: GetChunk<SaleDto>,
    searchChunk: SearchChunk<SaleDto>,
    events: Flow<SaleEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SearchableMapChunkViewModel<SaleDto, SaleUiModel>(
    get = getChunk,
    search = searchChunk,
    map = { it.map() },
    groupBy = { it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString() },
    logger = logger
), SaleContentInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
        events.onEach {
            refresh()
        }.launchIn(viewModelScope)
    }

    override fun select(sale: SaleUiModel) {
        launch { navigator.navigateToSale(sale.id) }
    }
}