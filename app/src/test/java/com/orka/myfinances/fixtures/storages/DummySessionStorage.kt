package com.orka.myfinances.fixtures.storages

import com.orka.myfinances.data.local.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel

class DummySessionStorage : LocalSessionStorage {
    override suspend fun get(): SessionModel? {
        TODO("Not yet implemented")
    }

    override suspend fun store(session: SessionModel) {

    }
}