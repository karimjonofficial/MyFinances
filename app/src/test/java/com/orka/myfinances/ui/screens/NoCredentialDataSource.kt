package com.orka.myfinances.ui.screens

import com.orka.myfinances.datasources.CredentialDataSource
import com.orka.myfinances.models.Credential

class NoCredentialDataSource : CredentialDataSource {
    override suspend fun get(username: String, password: String): Credential? {
        return null
    }
}
