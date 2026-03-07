package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
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
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class ReceiveContentViewModel(
    private val client: HttpClient,
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
    get = {
        try {
            val response = client.get("receives/")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<ReceiveApiModel>>()
            } else null
        } catch (_: Exception) {
            null
        }
    },
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
), IMapViewModel<ReceiveUiModel> {
    override val uiState = state.asStateFlow()

    init {
        events.onEach { initialize() }.launchIn(viewModelScope)
    }

    fun select(receive: ReceiveUiModel) {
        launch { navigator.navigateToReceive(receive.id) }
    }
}
