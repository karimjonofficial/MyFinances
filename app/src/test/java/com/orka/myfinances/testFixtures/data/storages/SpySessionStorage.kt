package com.orka.myfinances.testFixtures.data.storages

import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.zipped.SessionModel

class SpySessionStorage : LocalSessionStorage {
    var credential: Credential? = null

    override suspend fun get(): SessionModel? {
        return null
    }

    override suspend fun store(session: SessionModel) {
        this.credential = session.credential
    }
}
