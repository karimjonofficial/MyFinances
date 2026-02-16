package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.MapperListViewModel
import com.orka.myfinances.lib.viewmodel.list.ListViewModel
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
    private val navigator: Navigator,
    logger: Logger
) : MapperListViewModel<Sale, SaleUiModel>(
    loading = loading,
    failure = failure,
    repository = repository,
    map = { it.toUiModel() },
    logger = logger
), ListViewModel<SaleUiModel> {
    override val uiState = state.asStateFlow()

    init  {
        events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }

    fun select(sale: Sale) {
        launch { navigator.navigateToSale(sale) }
    }
}