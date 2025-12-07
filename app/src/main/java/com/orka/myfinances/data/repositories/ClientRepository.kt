package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.fixtures.resources.models.client2
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.data.Repository
import com.orka.myfinances.ui.screens.clients.AddClientRequest
import kotlinx.coroutines.delay

class ClientRepository : Repository<Client> {
    private var requests = 0

    override suspend fun get(): List<Client> {
        requests++
        val list = clients.toMutableList()
        repeat(requests) {
            list.add(client2)
        }
        delay(1000)
        return list.toList()
    }

    suspend fun add(request: AddClientRequest): Client? {
        delay(1000)
        return client2
    }
}