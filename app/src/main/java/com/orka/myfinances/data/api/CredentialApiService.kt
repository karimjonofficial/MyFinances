package com.orka.myfinances.data.api

import com.orka.myfinances.data.models.Credential

interface CredentialApiService {
    suspend fun get(username: String, password: String): Credential?
}