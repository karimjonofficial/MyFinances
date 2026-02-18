package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatNames
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.format.FormatTime
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SaleContentViewModel(
    events: Flow<SaleEvent>,
    loading: UiText,
    failure: UiText,
    repository: Get<Sale>,
    formatNames: FormatNames,
    priceFormatter: FormatPrice,
    dateFormatter: FormatDate,
    timeFormatter: FormatTime,
    private val navigator: Navigator,
    logger: Logger
) : MapViewModel<Sale, SaleUiModel>(
    loading = loading,
    failure = failure,
    get = repository,
    map = { sales ->
        sales.groupBy { sale -> sale.dateTime }
            .mapKeys { entry -> dateFormatter.formatDate(entry.key) }
            .mapValues { entry ->
                entry.value.map { sale ->
                    sale.toUiModel(
                        formatNames,
                        priceFormatter,
                        dateFormatter,
                        timeFormatter
                    )
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

    fun select(sale: Sale) {
        launch { navigator.navigateToSale(sale) }
    }
}