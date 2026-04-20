package com.orka.myfinances.ui.screens.order.details

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.viewmodel.StateFul
import kotlin.time.Instant

interface OrderScreenInteractor : StateFul {
    fun navigateToClient(clientId: Id)
    fun complete()
    fun setEndDate(endDateTime: Instant)

    companion object {
        val dummy = object : OrderScreenInteractor {
            override fun navigateToClient(clientId: Id) {}
            override fun complete() {}
            override fun setEndDate(endDateTime: Instant) {}
            override fun initialize() {}
            override fun refresh() {}
        }
    }
}