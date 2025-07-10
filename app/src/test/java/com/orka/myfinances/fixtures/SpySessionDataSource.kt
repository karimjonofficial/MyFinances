package com.orka.myfinances.fixtures

import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.models.Session

class SpySessionDataSource : SessionDataSource {
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