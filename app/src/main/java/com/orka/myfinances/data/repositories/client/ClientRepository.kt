package com.orka.myfinances.data.repositories.client

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.fixtures.resources.models.client2
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.fixtures.data.repositories.MockRepository

class ClientRepository : MockRepository<Client, AddClientRequest>(clients) {
    override fun map(request: AddClientRequest): Client {
        return client2
    }
}