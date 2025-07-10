package com.orka.myfinances.datasources

import com.orka.myfinances.models.Credential

interface CredentialDataSource {
    suspend fun get(username: String, password: String): Credential?
}