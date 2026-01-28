package com.orka.myfinances.ui.screens.clients

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.repositories.AddRepository
import com.orka.myfinances.lib.data.repositories.GetRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel

class ClientsScreenViewModel(
    getRepository: GetRepository<Client>,
    private val addRepository: AddRepository<Client, AddClientRequest>,
    loading: Int,
    failure: Int,
    logger: Logger
) : ListViewModel<Int, Client, Int>(
    loading = loading,
    failure = failure,
    repository = getRepository,
    logger = logger
) {

    fun add(name: String, lastName: String?, phone: String?, address: String?) = launch {
        val request = AddClientRequest(
            name = name,
            lastName = lastName,
            phone = phone,
            address = address
        )
        if(addRepository.add(request) != null)
            initialize()
    }
}