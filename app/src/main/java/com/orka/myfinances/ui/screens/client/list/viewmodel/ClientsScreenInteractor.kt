package com.orka.myfinances.ui.screens.client.list.viewmodel

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface ClientsScreenInteractor : StateFul {
    fun add(name: String, lastName: String?, patronymic: String?,phone: String?, address: String?)
    fun select(client: ClientUiModel)


    companion object {
        val dummy = object : ClientsScreenInteractor {
            override fun add(name: String, lastName: String?, patronymic: String?, phone: String?, address: String?) {}
            override fun select(client: ClientUiModel) {}
            override fun refresh() {}
        }
    }
}