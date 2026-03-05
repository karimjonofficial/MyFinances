package com.orka.myfinances.data.api.credential

import com.orka.myfinances.data.models.Credentials

interface CredentialApi {
    suspend fun get(request: AuthRequest): Credentials?
}