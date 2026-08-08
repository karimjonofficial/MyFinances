package com.orka.myfinances.application.viewmodels.debt.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.debt.DebtEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.SearchChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.chunk.SearchableMapChunkViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.models.ui.DebtUiModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.statuses.failure.failure
import com.orka.myfinances.ui.screens.debt.list.DebtsScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

class DebtsScreenViewModel(
    getChunk: GetChunk<DebtDto>,
    searchChunk: SearchChunk<DebtDto>,
    private val insert: Insert<AddDebtRequest>,
    events: Flow<DebtEvent>,
    logger: Logger,
    private val navigator: Navigator
) : SearchableMapChunkViewModel<DebtDto, DebtUiModel>(
    get = getChunk,
    search = searchChunk,
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results
            .groupBy { it.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { it.key.toString() }
            .mapValues { entry ->
                entry.value.map { model -> model.toUiModel() }
            }

        ChunkUiModel(
            size = chunk.count,
            pageIndex = chunk.pageIndex,
            nextPageIndex = chunk.nextPageIndex,
            previousPageIndex = chunk.previousPageIndex,
            content = map
        )
    },
    logger = logger
), DebtsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
        initialize()
    }

    override fun add(id: Id, price: Int, endDateTime: Instant?, description: String?) {
        tryTransition { oldState ->
            val request = AddDebtRequest(id, price, description, endDateTime)
            val created = insert.insert(request)
            if (created) oldState
            else State.Failure(failure, oldState.value)
        }
    }

    override fun select(debt: DebtUiModel) {
        launch {
            navigator.navigateToDebt(debt.id)
        }
    }
}
