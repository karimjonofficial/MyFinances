package com.orka.myfinances.application.factories

import android.util.Log
import com.orka.myfinances.core.baseUrl
import com.orka.myfinances.data.models.Credentials
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun httpClient(logger: Logger): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            this@install.logger = logger
            level = LogLevel.BODY
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        defaultRequest {
            url(urlString = baseUrl)
            contentType(ContentType.Application.Json)
        }
    }
}

fun httpClient(
    logger: Logger,
    credentials: Credentials,
    onUnauthorized: (Credentials) -> Unit
): HttpClient {
    return HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }

        install(Logging) {
            this@install.logger = logger
            level = LogLevel.BODY
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 10000
            connectTimeoutMillis = 10000
            socketTimeoutMillis = 10000
        }

        HttpResponseValidator {
            validateResponse  {
                if (it.status == HttpStatusCode.Unauthorized) {
                    Log.d("HttpClient.Validate", "Unauthorized")
                    onUnauthorized(credentials)
                }
            }
        }

        install(Auth) {
            bearer {
                loadTokens {
                    BearerTokens(
                        accessToken = credentials.access,
                        refreshToken = credentials.refresh
                    )
                }
            }
        }

        defaultRequest {
            url(urlString = baseUrl)
            contentType(ContentType.Application.Json)
        }
    }
}
