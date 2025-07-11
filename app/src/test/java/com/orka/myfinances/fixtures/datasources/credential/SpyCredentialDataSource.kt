package com.orka.myfinances.fixtures.datasources.credential

import com.orka.myfinances.datasources.CredentialDataSource
import com.orka.myfinances.models.Credential

class SpyCredentialDataSource : CredentialDataSource {
    var called = false

    override suspend fun get(username: String, password: String): Credential? {
        called = true
        return null
    }
}