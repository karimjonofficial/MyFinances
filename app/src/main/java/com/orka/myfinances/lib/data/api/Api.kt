package com.orka.myfinances.lib.data.api

import io.ktor.client.HttpClient

interface Api {
    val baseUrl: String
    val httpClient: HttpClient
}