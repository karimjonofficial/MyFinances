package com.orka.myfinances.application.viewmodels.debt.list

import com.orka.myfinances.data.api.debt.DebtApi
import com.orka.myfinances.data.api.debt.DebtApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.data.api.scoped.office.getChunk
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.ChunkMapState
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapChunkViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.debt.list.DebtUiModel
import com.orka.myfinances.ui.screens.debt.list.interactor.DebtsScreenInteractor
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

class DebtsScreenViewModel(
    private val debtApi: DebtApi,
    private val formatPrice: FormatPrice,
    private val formatLocalDate: FormatLocalDate,
    private val formatTime: FormatTime,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger,
    private val navigator: Navigator
) : MapChunkViewModel<DebtApiModel, DebtUiModel>(
    loading = loading,
    failure = failure,
    get = { size, page -> debtApi.getChunk(size, page) },
    map = { chunk ->
        val timeZone = TimeZone.currentSystemDefault()
        val map = chunk.results
            .groupBy { it.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { formatLocalDate.formatLocalDate(it.key) }
            .mapValues { entry ->
                entry.value.map { model -> model.toUiModel(formatPrice, formatTime) }
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
), DebtsScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun add(id: Id, price: Int, endDateTime: Instant?, description: String?) {
        launch {
            val oldState = state.value
            try {
                setState(State.Loading(loading, oldState.value))
                val request = AddDebtRequest(id, price, description, endDateTime)
                if (debtApi.add(request)) {
                    refresh()
                } else setState(State.Failure(failure, oldState.value))
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
