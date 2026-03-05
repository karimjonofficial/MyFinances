package com.orka.myfinances.fixtures.data.api

import com.orka.myfinances.data.api.credential.AuthRequest
import com.orka.myfinances.data.api.credential.CredentialApi
import com.orka.myfinances.data.models.Credentials
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class CredentialApiImpl(private val client: HttpClient) : CredentialApi {
    override suspend fun get(request: AuthRequest): Credentials? {
        return (client.post(
            urlString = "auth/token/",
            block = { setBody(request) }
        ).body() as CredentialsModel?)?.map()
    }
}