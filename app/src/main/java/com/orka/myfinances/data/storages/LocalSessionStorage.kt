package com.orka.myfinances.data.storages

import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.zipped.SessionModel

interface LocalSessionStorage {
    suspend fun get(): SessionModel?
    suspend fun store(session: SessionModel)
    suspend fun updateCredentials(credentials: Credentials)
    suspend fun clear()
}