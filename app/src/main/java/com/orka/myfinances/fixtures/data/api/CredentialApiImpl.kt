package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.fixtures.resources.models.credential

class CredentialApiImpl : CredentialApi {
    override suspend fun get(username: String, password: String): Credential {
        return credential
    }
}