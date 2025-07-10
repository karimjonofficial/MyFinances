package com.orka.myfinances.fixtures

import com.orka.myfinances.models.Credential
import com.orka.myfinances.datasources.CredentialDataSource

class SpyCredentialDataSource : CredentialDataSource {
    var called = false

    override suspend fun get(username: String, password: String): Credential? {
        called = true
        return null
    }
}