package com.orka.myfinances.data.sources

import com.orka.myfinances.data.models.Credentials

interface CredentialDataSource {
    suspend fun get(username: String, password: String): Credentials?
}