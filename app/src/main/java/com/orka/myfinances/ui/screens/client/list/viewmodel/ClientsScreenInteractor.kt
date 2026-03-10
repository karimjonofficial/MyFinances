package com.orka.myfinances.ui.screens.client.list.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ClientsScreenInteractor : StateFul {
    fun add(name: String, lastName: String?, phone: String?, address: String?)
    fun select(client: ClientModel)
}