package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.MapViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface SaleContentInteractor : StateFul, MapViewModel<SaleUiModel> {
    fun select(sale: SaleUiModel)
    fun back()
}