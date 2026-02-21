package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class ReceiveContentViewModel(
    events: Flow<ReceiveEvent>,
    repository: Get<Receive>,
    loading: UiText,
    failure: UiText,
    formatNames: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime,
    private val navigator: Navigator,
    logger: Logger
) : MapViewModel<Receive, ReceiveUiModel>(
    loading = loading,
    failure = failure,
    get = repository,
    map = { receives ->
        receives.groupBy { receive -> receive.dateTime }
            .mapKeys { entry -> dateFormatter.formatDate(entry.key) }
            .mapValues { entry ->
                entry.value.map { receive ->
                    receive.toUiModel(
                        formatNames,
                        priceFormatter,
                        dateFormatter,
                        timeFormatter
                    )
                }
            }
    },
    logger = logger
), IMapViewModel<ReceiveUiModel> {
    override val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    fun select(receive: Receive) {
        launch { navigator.navigateToReceive(receive) }
    }
}
