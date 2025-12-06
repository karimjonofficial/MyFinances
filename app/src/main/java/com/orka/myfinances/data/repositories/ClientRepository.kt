package com.orka.myfinances.data.repositories

import com.orka.myfinances.lib.data.Repository
import com.orka.myfinances.data.models.Client
import com.orka.myfinances.fixtures.resources.models.clients

class ClientRepository : Repository<Client> {
    override suspend fun get(): List<Client> {
        return clients
    }
}