package com.orka.myfinances.fixtures.datasources.session

import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.lib.credential
import com.orka.myfinances.models.Session

class StubSessionDataSource : SessionDataSource {
    override suspend fun get(): Session? {
        return Session(credential)
    }

    override suspend fun store(session: Session) {

    }
}