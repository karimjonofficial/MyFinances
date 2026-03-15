package com.orka.myfinances.application.viewmodels.history

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.ReceiveApiModel
import com.orka.myfinances.data.api.receive.map
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
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

class ReceiveContentViewModel(
    private val receiveApi: ReceiveApi,
    events: Flow<ReceiveEvent>,
    loading: UiText,
    failure: UiText,
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatTime: FormatTime,
    formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    logger: Logger
) : MapViewModel<ReceiveApiModel, ReceiveUiModel>(
    loading = loading,
    failure = failure,
    get = { receiveApi.getAll() },
    map = { receives ->
        receives.groupBy { receive -> receive.dateTime }
            .mapKeys { entry -> formatDate.formatDate(entry.key) }
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