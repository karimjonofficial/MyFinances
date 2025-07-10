package com.orka.myfinances.fixtures

import com.orka.myfinances.models.Session
import com.orka.myfinances.core.SessionDataSource

class NoSessionSessionDataSource : SessionDataSource {
    override suspend fun get(): Session? {
        return null
    }

    override suspend fun store(session: Session) {

    }
}