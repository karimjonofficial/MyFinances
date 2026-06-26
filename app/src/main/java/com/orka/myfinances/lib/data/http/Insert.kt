package com.orka.myfinances.lib.data.http

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpClient.insert(
    baseUrl: String,
    request: T
): Boolean {
    val response = post(baseUrl) {
        setBody(request)
    }
    return response.status == HttpStatusCode.Created
}
