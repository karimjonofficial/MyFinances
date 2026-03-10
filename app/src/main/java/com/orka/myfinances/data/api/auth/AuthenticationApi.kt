package com.orka.myfinances.data.api.auth

import com.orka.myfinances.data.models.Credentials
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class AuthenticationApi(private val client: HttpClient) {
    suspend fun get(username: String, password: String): Credentials? {
        val response = client.post(
            urlString = "auth/token/",
            block = { setBody(AuthRequest(username, password)) }
        )

        return if (response.status == HttpStatusCode.OK)
            response.body<CredentialsModel>().map()
        else null
    }

    suspend fun refresh(refresh: String): Credentials? {
        val response = client.post(
            urlString = "auth/token/refresh/",
            block = { setBody(RefreshRequest(refresh)) }
        )

        return if (response.status == HttpStatusCode.OK)
            response.body<CredentialsModel>().map()
        else null
    }
}