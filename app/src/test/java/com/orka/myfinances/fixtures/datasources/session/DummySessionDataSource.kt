package com.orka.myfinances.fixtures.datasources.session

import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.models.Session

class DummySessionDataSource : SessionDataSource {
    override suspend fun get(): Session? {
        throw NotImplementedError()
    }

    override suspend fun store(session: Session) {}
}