package com.orka.myfinances.ui.screens.clients

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.ui.models.Text
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class ClientsScreenViewModel(
    get: Get<Client>,
    private val add: Add<Client, AddClientRequest>,
    loading: Text,
    failure: Text,
    logger: Logger
) : ListViewModel<Client>(
    loading = loading,
    failure = failure,
    repository = get,
    logger = logger
) {

    fun add(name: String, lastName: String?, phone: String?, address: String?) = launch {
        val request = AddClientRequest(
            name = name,
            lastName = lastName,
            phone = phone,
            address = address
        )
        if(add.add(request) != null)
            initialize()
    }
}