package com.orka.myfinances.application.manager

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.storages.SessionStorage
import com.orka.myfinances.data.zipped.SessionModel

class EmptySessionStorage : SessionStorage {
    override suspend fun get(): SessionModel? {
        return null
    }

    override suspend fun store(session: SessionModel) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCredentials(credentials: Credentials) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}