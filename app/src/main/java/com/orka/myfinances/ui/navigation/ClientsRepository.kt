package com.orka.myfinances.ui.navigation

import com.orka.myfinances.lib.data.Repository

class ClientsRepository : Repository<Client> {
    override suspend fun get(): List<Client>? {
        TODO("Not yet implemented")
    }
}