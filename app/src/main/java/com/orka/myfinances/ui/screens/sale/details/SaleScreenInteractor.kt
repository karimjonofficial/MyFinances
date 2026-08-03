package com.orka.myfinances.ui.screens.sale.details

import com.orka.myfinances.data.models.Id

interface SaleScreenInteractor {
    fun navigateToClient(clientId: Id)
    fun print()
    fun back()

    companion object {
        val dummy = object : SaleScreenInteractor {
            override fun print() {}
            override fun navigateToClient(clientId: Id) {}
            override fun back() {}
        }
    }
}
