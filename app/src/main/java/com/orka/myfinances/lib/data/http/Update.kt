package com.orka.myfinances.lib.data.http

import io.ktor.client.HttpClient
import io.ktor.client.request.patch
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpClient.update(
    baseUrl: String,
    id: Int,
    request: T
): Boolean {
    val response = put("$baseUrl$id/") {
        setBody(request)
    }
    return response.status == HttpStatusCode.OK || response.status == HttpStatusCode.NoContent
}
