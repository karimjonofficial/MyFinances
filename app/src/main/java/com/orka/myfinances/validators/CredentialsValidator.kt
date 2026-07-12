package com.orka.myfinances.validators

import com.orka.myfinances.data.models.Credentials

interface CredentialsValidator {
    //TODO validate should store credentials if they get updated while validation
    suspend fun validate(credentials: Credentials): Credentials?
}