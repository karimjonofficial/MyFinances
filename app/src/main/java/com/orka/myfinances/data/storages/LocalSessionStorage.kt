package com.orka.myfinances.data.storages

import com.orka.myfinances.data.zipped.SessionModel
import com.orka.myfinances.fixtures.data.api.CredentialsModel

interface LocalSessionStorage {
    suspend fun get(): SessionModel?
    suspend fun store(session: SessionModel)
    suspend fun updateCredentials(credentials: CredentialsModel)
    suspend fun clear()
}