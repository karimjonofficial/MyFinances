package com.orka.myfinances.data.repositories.auth

import com.orka.myfinances.data.api.auth.AuthenticationApi
import com.orka.myfinances.data.models.Credentials

class AuthenticationRepository(
    private val api: AuthenticationApi
) : Authenticator {
    override suspend fun authenticate(username: String, password: String): Credentials? {
        return api.get(username, password)
    }

    override suspend fun refresh(refresh: String): Credentials? {
        return api.refresh(refresh)
    }
}