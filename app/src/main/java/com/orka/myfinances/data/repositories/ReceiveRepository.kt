package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.fixtures.resources.models.receive.receives
import com.orka.myfinances.lib.data.Repository
import kotlinx.coroutines.delay

class ReceiveRepository : Repository<Receive> {
    override suspend fun get(): List<Receive> {
        delay(1000)
        return receives
    }
}