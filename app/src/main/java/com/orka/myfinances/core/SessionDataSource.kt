package com.orka.myfinances.core

import com.orka.myfinances.models.Session

interface SessionDataSource {
    suspend fun get(): Session?
    suspend fun store(session: Session)
}