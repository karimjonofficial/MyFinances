package com.orka.myfinances.ui.managers

import com.orka.myfinances.data.local.LocalSessionStorage
import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.lib.extensions.toModel
import com.orka.myfinances.testLib.session

class StubSessionStorage : LocalSessionStorage {
    override suspend fun get(): SessionModel {
        return session.toModel()
    }

    override suspend fun store(session: SessionModel) {

    }
}
