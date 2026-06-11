package com.orka.myfinances.application.viewmodels.debt.list

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.debt.getChunk
import com.orka.myfinances.data.api.debt.models.response.DebtApiModel
import com.orka.myfinances.data.api.debt.toApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.debt.DebtEvent
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel
import com.orka.myfinances.ui.screens.debt.list.interactor.DebtsScreenInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

class DebtsScreenViewModel(
    private val debtApi: DebtApi,
    events: Flow<DebtEvent>,
    private val formatPrice: FormatPrice,
    private val formatLocalDate: FormatLocalDate,
    private val formatTime: FormatTime,
    loading: UiText,
    failure: UiText,
    logger: Logger,
    private val navigator: Navigator
) : MapChunkViewModel<DebtApiModel, DebtUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> debtApi.getChunk(size, page, false) },
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
), DebtsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
        initialize()
    }

    override fun add(id: Id, price: Int, endDateTime: Instant?, description: String?) {
        launch {
            val oldState = state.value
            try {
                setState(State.Loading(loading, oldState.value))
                val request = AddDebtRequest(id, price, description, endDateTime)
                val created = debtApi.insert(
                    request = request,
                    map = AddDebtRequest::toApiRequest
                )
                if (created) refresh()
                else setState(State.Failure(failure, oldState.value))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString()), oldState.value))
            }
        }
    }

    override fun select(debt: DebtUiModel) {
        launch {
            navigator.navigateToDebt(debt.id)
        }
    }
}
