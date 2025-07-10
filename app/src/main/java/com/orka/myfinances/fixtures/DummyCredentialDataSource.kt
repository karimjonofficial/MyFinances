package com.orka.myfinances.fixtures

import com.orka.myfinances.models.Credential
import com.orka.myfinances.datasources.CredentialDataSource
import kotlinx.coroutines.delay

class DummyCredentialDataSource : CredentialDataSource {
    override suspend fun get(username: String, password: String): Credential? {
        delay(5000)
        return null
    }
}
