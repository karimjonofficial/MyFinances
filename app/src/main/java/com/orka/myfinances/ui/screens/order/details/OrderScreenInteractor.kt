package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface OrderScreenInteractor : StateFul {
    fun navigateToClient(clientId: Id)

    companion object {
        val dummy = object : OrderScreenInteractor {
            override fun navigateToClient(clientId: Id) {}
            override fun initialize() {}
            override fun refresh() {}
        }
    }
}