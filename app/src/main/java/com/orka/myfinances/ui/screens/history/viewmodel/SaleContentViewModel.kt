package com.orka.myfinances.ui.screens.history.viewmodel

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.sale.Sale
import com.orka.myfinances.data.repositories.sale.SaleEvent
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.Text
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SaleContentViewModel(
    events: Flow<SaleEvent>,
    loading: Text,
    failure: Text,
    repository: Get<Sale>,
    logger: Logger
) : ListViewModel<Sale>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger
) {
    init  {
        events.onEach {
            initialize()
        }.launchIn(viewModelScope)
    }
}