package com.orka.myfinances.fixtures

import com.orka.myfinances.data.models.Credential
import com.orka.myfinances.data.sources.CredentialDataSource
import kotlinx.coroutines.delay

class CredentialDataSourceImpl : CredentialDataSource {
    override suspend fun get(username: String, password: String): Credential? {
        delay(delayDurationInMillis)
        return if(username == "admin" && password == "123") credential else null
    }
}
