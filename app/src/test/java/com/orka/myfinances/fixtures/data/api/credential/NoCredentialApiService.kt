package com.orka.myfinances.fixtures.data.api.credential

import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.models.Credential

class NoCredentialApiService : CredentialApiService {
    override suspend fun get(username: String, password: String): Credential? {
        return null
    }
}