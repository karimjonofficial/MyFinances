package com.orka.myfinances.data.repositories

import com.orka.myfinances.data.models.Session

interface SessionRepository {
    suspend fun get(): Session?
    suspend fun store(session: Session)
}