package com.orka.myfinances.fixtures.storages

import com.orka.myfinances.data.local.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel

class EmptySessionStorage : LocalSessionStorage {
    override suspend fun get(): SessionModel? {
        return null
    }

    override suspend fun store(session: SessionModel) {

    }
}