package com.orka.myfinances.ui.screens.history.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface SaleContentInteractor : StateFul {
    fun select(sale: SaleUiModel)
    fun back()
}