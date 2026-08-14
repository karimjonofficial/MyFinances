package com.orka.myfinances.application.viewmodels.debt.history

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.application.viewmodels.debt.list.toUiModel
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.repositories.debt.DebtEvent
import com.orka.myfinances.data.repositories.debt.GetDebtsChunk
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.DebtUiModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.history.DebtsHistoryContentInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DebtsHistoryContentViewModel(
    private val getDebtsChunk: GetDebtsChunk,
    events: Flow<DebtEvent>,
    logger: Logger,
    private val navigator: Navigator
) : SearchableMapChunkViewModel<DebtDto, DebtUiModel>(
    get = { size, page -> getDebtsChunk.getDebtsChunk(size, page, true, null) },
    search = { size, page, q -> getDebtsChunk.getDebtsChunk(size, page, true, q) },
    map = { it.toUiModel() },
    groupBy = { it.dateTime.toLocalDateTime(TimeZone.currentSystemDefault()).date.toString() },
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
