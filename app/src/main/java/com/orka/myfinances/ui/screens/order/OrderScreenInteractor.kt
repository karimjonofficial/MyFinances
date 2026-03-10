package com.orka.myfinances.ui.screens.order

import com.orka.myfinances.data.models.Id

interface OrderScreenInteractor {
    fun initialize()
    fun navigateToClient(clientId: Id)
}