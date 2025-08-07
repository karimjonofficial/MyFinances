package com.orka.myfinances.fixtures.data.sources.session

import com.orka.myfinances.data.repositories.SessionRepository
import com.orka.myfinances.data.models.Session

class SpySessionRepository : SessionRepository {
    var getCalled = false
    var storeCalled = false

    override suspend fun get(): Session? {
        getCalled = true
        return null
    }

    override suspend fun store(session: Session) {
        storeCalled = true
    }
}