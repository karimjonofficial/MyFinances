package com.orka.myfinances.ui.screens.sale.list

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface SaleContentInteractor : StateFul, ChunkViewModel<SaleUiModel> {
    fun select(sale: SaleUiModel)
}