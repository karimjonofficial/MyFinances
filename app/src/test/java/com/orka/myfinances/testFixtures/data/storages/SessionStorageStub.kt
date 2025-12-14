package com.orka.myfinances.testFixtures.data.storages

import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.lib.extensions.models.toModel
import com.orka.myfinances.testFixtures.resources.models.session

class SessionStorageStub : LocalSessionStorage {
    override suspend fun get(): SessionModel {
        return session.toModel()
    }

    override suspend fun store(session: SessionModel) {

    }
}
