package com.orka.myfinances.fixtures.data.sources.session

import com.orka.myfinances.data.repositories.SessionRepository
import com.orka.myfinances.testLib.companyOffice
import com.orka.myfinances.testLib.credential
import com.orka.myfinances.testLib.user
import com.orka.myfinances.data.models.Session

class StubSessionRepository : SessionRepository {
    override suspend fun get(): Session? {
        return Session(user, credential, companyOffice)
    }

    override suspend fun store(session: Session) {

    }
}