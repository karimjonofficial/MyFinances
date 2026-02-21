package com.orka.myfinances.data.repositories.client

import com.orka.myfinances.data.models.Client
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.fixtures.resources.models.client2
import com.orka.myfinances.fixtures.resources.models.clients
import com.orka.myfinances.lib.fixtures.data.repositories.MockAddRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetByIdRepository
import com.orka.myfinances.lib.fixtures.data.repositories.MockGetRepository

class ClientRepository : MockGetRepository<Client>, MockAddRepository<Client, AddClientRequest>, MockGetByIdRepository<Client> {
    override val items = clients.toMutableList()

    override suspend fun List<Client>.find(id: Id): Client? {
        return find { it.id == id }
    }

    override suspend fun AddClientRequest.map(): Client {
        return client2
    }
}