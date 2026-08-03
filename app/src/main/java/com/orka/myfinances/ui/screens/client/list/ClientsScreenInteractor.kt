package com.orka.myfinances.ui.screens.client.list

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.ClientUiModel

interface ClientsScreenInteractor : Refreshable, PaginatedSearchable {
    fun add(name: String, lastName: String?, patronymic: String?, phone: String?, address: String?)
    fun select(client: ClientUiModel)


    companion object {
        val dummy = object : ClientsScreenInteractor {
            override fun add(name: String, lastName: String?, patronymic: String?, phone: String?, address: String?) {}
            override fun select(client: ClientUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
