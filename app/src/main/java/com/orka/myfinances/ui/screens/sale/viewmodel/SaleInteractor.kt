package com.orka.myfinances.ui.screens.sale.viewmodel

import com.orka.myfinances.data.models.Id

interface SaleInteractor {
    fun navigateToClient(clientId: Id)
    fun back()
}