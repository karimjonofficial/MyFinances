package com.orka.myfinances.fixtures

import com.orka.myfinances.models.Session
import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.credential

class StubSessionDataSource : SessionDataSource {
    override suspend fun get(): Session? {
        return Session(credential)
    }

    override suspend fun store(session: Session) {

    }
}