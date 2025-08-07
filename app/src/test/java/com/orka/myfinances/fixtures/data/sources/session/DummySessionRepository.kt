package com.orka.myfinances.fixtures.data.sources.session

import com.orka.myfinances.data.repositories.SessionRepository
import com.orka.myfinances.data.models.Session

class DummySessionRepository : SessionRepository {
    override suspend fun get(): Session? {
        throw NotImplementedError()
    }

    override suspend fun store(session: Session) {}
}