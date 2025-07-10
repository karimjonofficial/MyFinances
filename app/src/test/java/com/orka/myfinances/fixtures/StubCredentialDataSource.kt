package com.orka.myfinances.fixtures

import com.orka.myfinances.models.Credential
import com.orka.myfinances.datasources.CredentialDataSource

class StubCredentialDataSource : CredentialDataSource {
    override suspend fun get(username: String, password: String): Credential {
        return Credential(1)
    }
}