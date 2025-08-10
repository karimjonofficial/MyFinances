package com.orka.myfinances.fixtures

import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel

class LocalSessionStorageImpl : LocalSessionStorage {
    override suspend fun get(): SessionModel? {
        TODO("Not yet implemented")
    }

    override suspend fun store(session: SessionModel) {
        TODO("Not yet implemented")
    }
}