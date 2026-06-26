package com.orka.myfinances.application.viewmodels.debt.history

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.debt.list.toUiModel
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.repositories.debt.DebtEvent
import com.orka.myfinances.data.repositories.debt.DebtRepository
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.history.DebtsHistoryContentInteractor
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DebtsHistoryContentViewModel(
    private val repository: DebtRepository,
    events: Flow<DebtEvent>,
    private val formatPrice: FormatPrice,
    private val formatLocalDate: FormatLocalDate,
    private val formatTime: FormatTime,
    loading: UiText,
    failure: UiText,
    logger: Logger,
    private val navigator: Navigator
) : MapChunkViewModel<DebtDto, DebtUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page, query -> repository.getDebtsChunk(size, page, true, query) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results
            .groupBy { it.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { formatLocalDate.formatLocalDate(it.key) }
            .mapValues { entry ->
                entry.value.map { model -> model.toUiModel(formatPrice, formatTime) }
            }

        ChunkUiModel(
            count = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), DebtsHistoryContentInteractor {
    val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
        initialize()
    }

    override fun select(item: DebtUiModel) {
        launch {
            navigator.navigateToDebt(item.id)
        }
    }
}
