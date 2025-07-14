package com.orka.myfinances.fixtures

import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.models.Session
import kotlinx.coroutines.delay

class SessionDataSourceImpl : SessionDataSource {
    private var session: Session? = null

    override suspend fun get(): Session? {
        delay(delayDurationInMillis)
        return session
    }

    override suspend fun store(session: Session) {
        delay(delayDurationInMillis)
        this.session = session
    }
}