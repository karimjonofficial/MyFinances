package com.orka.myfinances.fixtures.datasources.session

import com.orka.myfinances.core.SessionDataSource
import com.orka.myfinances.models.Session

class NoSessionSessionDataSource : SessionDataSource {
    override suspend fun get(): Session? {
        return null
    }

    override suspend fun store(session: Session) {

    }
}