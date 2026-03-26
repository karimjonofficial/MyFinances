package com.orka.myfinances.ui.screens.sale.details.interactor

import com.orka.myfinances.data.models.Id

interface SaleScreenInteractor {
    fun initialize()
    fun navigateToClient(clientId: Id)
    fun back()
}