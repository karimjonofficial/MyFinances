package com.orka.myfinances.testFixtures.data.api.credential

import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.fixtures.resources.models.credential
import com.orka.myfinances.data.models.Credential

class CredentialApiStub : CredentialApi {
    override suspend fun get(username: String, password: String): Credential {
        return credential
    }
}