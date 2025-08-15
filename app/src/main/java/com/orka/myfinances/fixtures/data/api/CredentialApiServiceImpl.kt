package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.CredentialApiService
import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.fixtures.credential

class CredentialApiServiceImpl : CredentialApiService {
    override suspend fun get(username: String, password: String): Credential? {
        return credential
    }
}
