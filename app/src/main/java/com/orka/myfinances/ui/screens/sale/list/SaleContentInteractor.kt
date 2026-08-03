package com.orka.myfinances.ui.screens.sale.list

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.ui.models.ui.SaleUiModel

interface SaleContentInteractor : PaginatedSearchable {
    fun select(sale: SaleUiModel)
}
