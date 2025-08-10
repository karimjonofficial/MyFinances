package com.orka.myfinances.fixtures.data.storages

import com.orka.myfinances.data.storages.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.lib.extensions.toModel
import com.orka.myfinances.testLib.session

class SessionStorageStub : LocalSessionStorage {
    override suspend fun get(): SessionModel {
        return session.toModel()
    }

    override suspend fun store(session: SessionModel) {

    }
}
