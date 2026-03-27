package com.orka.myfinances.ui.screens.sale.list

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel

interface SaleContentInteractor : ChunkViewModel {
    fun select(sale: SaleUiModel)
}