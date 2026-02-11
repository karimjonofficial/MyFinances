package com.orka.myfinances.testFixtures.data.api.credential

import com.orka.myfinances.data.api.CredentialApi
import com.orka.myfinances.data.models.Credential

class SpyCredentialApi : CredentialApi {
    var called = false

    override suspend fun get(username: String, password: String): Credential? {
        called = true
        return null
    }
}