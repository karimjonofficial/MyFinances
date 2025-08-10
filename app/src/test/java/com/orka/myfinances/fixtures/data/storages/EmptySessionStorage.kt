package com.orka.myfinances.fixtures.data.storages

import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel

class EmptySessionStorage : LocalSessionStorage {
    override suspend fun get(): SessionModel? {
        return null
    }

    override suspend fun store(session: SessionModel) {

    }
}