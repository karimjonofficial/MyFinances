package com.orka.myfinances.application.viewmodels.history

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.sale.SaleApi
import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.history.viewmodel.SaleContentInteractor
import com.orka.myfinances.ui.screens.history.viewmodel.SaleUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SaleContentViewModel(
    private val saleApi: SaleApi,
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
    get = { saleApi.getAll() },
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
), IMapViewModel<SaleUiModel>, SaleContentInteractor {
    override val uiState = state.asStateFlow()

    init {
        events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    override fun select(sale: SaleUiModel) {
        launch { navigator.navigateToSale(sale.id) }
    }

    override fun back() {
        launch { navigator.back() }
    }
}