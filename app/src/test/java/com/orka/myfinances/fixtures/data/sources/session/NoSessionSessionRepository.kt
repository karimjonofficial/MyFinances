package com.orka.myfinances.fixtures.data.sources.session

import com.orka.myfinances.data.repositories.SessionRepository
import com.orka.myfinances.data.models.Session

class NoSessionSessionRepository : SessionRepository {
    override suspend fun get(): Session? {
        return null
    }

    override suspend fun store(session: Session) {

    }
}