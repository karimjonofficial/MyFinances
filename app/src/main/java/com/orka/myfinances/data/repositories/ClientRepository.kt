package com.orka.myfinances.data.repositories

import com.orka.myfinances.lib.data.Repository
import com.orka.myfinances.data.models.Client

class ClientRepository : Repository<Client> {
    override suspend fun get(): List<Client> {
        return emptyList()
    }
}