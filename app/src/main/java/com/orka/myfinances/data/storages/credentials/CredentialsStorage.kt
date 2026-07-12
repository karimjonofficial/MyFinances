package com.orka.myfinances.data.storages.credentials

import com.orka.myfinances.data.models.Credentials

interface CredentialsStorage {
    suspend fun get(): Credentials?
    suspend fun set(credentials: Credentials)
    suspend fun clear()
}