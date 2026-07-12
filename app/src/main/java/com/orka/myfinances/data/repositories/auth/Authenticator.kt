package com.orka.myfinances.data.repositories.auth

import com.orka.myfinances.data.models.Credentials

interface Authenticator {
    suspend fun authenticate(username: String, password: String): Credentials?
    suspend fun refresh(refresh: String): Credentials?
}