package com.orka.myfinances.application.validators

import com.orka.myfinances.data.api.auth.map
import com.orka.myfinances.data.api.auth.models.response.CredentialsApiModel
import com.orka.myfinances.data.models.Credentials
import com.orka.myfinances.data.storages.credentials.CredentialsStorage
import com.orka.myfinances.validators.CredentialsValidator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode

class CredentialsValidatorImpl(
    private val httpClient: HttpClient,
    private val credentialsStorage: CredentialsStorage,
) : CredentialsValidator {
    override suspend fun validate(credentials: Credentials): Credentials? {
        val response = httpClient.get(
            urlString = "users/me/",
            block = {
                header("Authorization", "Bearer ${credentials.access}")
            }
        )

        return if(response.status == HttpStatusCode.Unauthorized) {
            val r = httpClient.post(
                urlString = "token/refresh/",
                block = {
                    header("Authorization", "Bearer ${credentials.refresh}")
                }
            ).body<CredentialsApiModel>().map()
            credentialsStorage.set(r)
            r
        } else response.body<CredentialsApiModel>().map()
    }
}