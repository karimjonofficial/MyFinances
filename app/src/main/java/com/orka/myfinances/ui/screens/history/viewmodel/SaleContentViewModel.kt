package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
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

class SaleContentViewModel(
    private val client: HttpClient,
    events: Flow<SaleEvent>,
    loading: UiText,
    failure: UiText,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    formatDateTime: FormatDateTime,
    private val navigator: Navigator,
    logger: Logger
) : MapViewModel<SaleApiModel, SaleUiModel>(
    loading = loading,
    failure = failure,
    get = {
        try {
            val response = client.get("sales/")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<SaleApiModel>>()
            } else null
        } catch (_: Exception) {
            null
        }
    },
    map = { sales ->
        sales.groupBy { sale -> sale.dateTime }
            .mapKeys { entry -> dateFormatter.formatDate(entry.key) }
            .mapValues { entry ->
                entry.value.map { sale ->
                    sale.map(priceFormatter, formatDateTime)
                }
            }
    },
    logger = logger
), IMapViewModel<SaleUiModel> {
    override val uiState = state.asStateFlow()

    init {
        events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    fun select(sale: SaleUiModel) {
        launch { navigator.navigateToSale(sale.id) }
    }
}