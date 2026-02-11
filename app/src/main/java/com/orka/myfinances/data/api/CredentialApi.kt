package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.Credential

interface CredentialApi {
    suspend fun get(username: String, password: String): Credential?
}