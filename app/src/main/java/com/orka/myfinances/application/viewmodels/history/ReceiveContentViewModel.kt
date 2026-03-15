package com.orka.myfinances.application.viewmodels.history

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.ReceiveApiModel
import com.orka.myfinances.data.api.receive.map
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatLocalDate
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
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
    private val receiveApi: ReceiveApi,
    events: Flow<ReceiveEvent>,
    loading: UiText,
    failure: UiText,
    formatPrice: FormatPrice,
    formatLocalDate: FormatLocalDate,
    formatTime: FormatTime,
    formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    logger: Logger
) : MapViewModel<ReceiveApiModel, ReceiveUiModel>(
    loading = loading,
    failure = failure,
    get = { receiveApi.getAll() },
    map = { receives ->
        val timeZone = TimeZone.currentSystemDefault()
        receives.groupBy { receive -> receive.dateTime.toLocalDateTime(timeZone).date }
            .mapKeys { entry -> formatLocalDate.formatLocalDate(entry.key) }
            .mapValues { entry ->
                entry.value.map { receive ->
                    receive.map(
                        formatPrice,
                        formatDecimal,
                        formatTime
                    )
                }
            }
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