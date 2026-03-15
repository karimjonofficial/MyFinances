package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.data.models.Id

interface OrderScreenInteractor {
    fun initialize()
    fun navigateToClient(clientId: Id)
}