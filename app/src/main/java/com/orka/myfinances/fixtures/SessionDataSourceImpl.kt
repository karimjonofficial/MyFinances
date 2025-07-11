package com.orka.myfinances.fixtures

import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.models.Session
import kotlinx.coroutines.delay

class SessionDataSourceImpl : SessionDataSource {

    override suspend fun get(): Session? {
        delay(5000)
        return null
    }

    override suspend fun store(session: Session) {
        delay(5000)
    }
}