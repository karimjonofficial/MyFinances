package com.orka.myfinances.ui.screens.clients

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.repositories.client.ClientRepository
import com.orka.myfinances.lib.ui.viewmodel.ListViewModel
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.repositories.client.AddClientRequest
import kotlinx.coroutines.CoroutineScope

class ClientsScreenViewModel(
    val repository: ClientRepository,
    loading: String,
    failure: String,
    logger: Logger,
    coroutineScope: CoroutineScope
) : ListViewModel<String, Client, String>(
    loading = loading,
    failure = failure,
    repository = repository,
    logger = logger,
    coroutineScope = coroutineScope
) {
    fun add(name: String, lastName: String?, phone: String?, address: String?) = launch {
        val request = AddClientRequest(
            name = name,
            lastName = lastName,
            phone = phone,
            address = address
        )
        if(repository.add(request) != null)
            initialize()
    }
}