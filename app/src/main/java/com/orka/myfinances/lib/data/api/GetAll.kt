package com.orka.myfinances.lib.data.api

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> Api.getAll(): List<T>? {
    val response = httpClient.get(baseUrl)
    return if (response.status == HttpStatusCode.OK) response.body() else null
}