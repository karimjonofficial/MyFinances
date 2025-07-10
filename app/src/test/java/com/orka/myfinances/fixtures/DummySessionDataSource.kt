package com.orka.myfinances.fixtures

import com.orka.myfinances.models.Session
import com.orka.myfinances.core.SessionDataSource

class DummySessionDataSource : SessionDataSource {
    override suspend fun get(): Session? {
        throw NotImplementedError()
    }

    override suspend fun store(session: Session) {}
}