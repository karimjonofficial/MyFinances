package com.orka.myfinances.validators

import com.orka.myfinances.data.models.Credentials

interface CredentialsValidator {
    suspend fun validate(credentials: Credentials): Credentials?
}