package com.orka.myfinances.fixtures.data.api.credential

import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.models.Credential

class SpyCredentialApiService : CredentialApiService {
    var called = false

    override suspend fun get(username: String, password: String): Credential? {
        called = true
        return null
    }
}