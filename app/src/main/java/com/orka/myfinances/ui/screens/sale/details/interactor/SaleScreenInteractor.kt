package com.orka.myfinances.ui.screens.sale.details.interactor

import com.orka.myfinances.data.models.Id

interface SaleScreenInteractor {
    fun initialize()
    fun navigateToClient(clientId: Id)
    fun print()
    fun back()

    companion object {
        val dummy = object : SaleScreenInteractor {
            override fun initialize() {}
            override fun print() {}
            override fun navigateToClient(clientId: Id) {}
            override fun back() {}
        }
    }
}