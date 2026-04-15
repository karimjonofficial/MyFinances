package com.orka.myfinances.lib.data.api

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T, reified R> Api<*>.insert(
    request: T,
    map: T.() -> R
): Boolean {
    val apiRequest = request.map()
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(apiRequest) }
    )
    return response.status == HttpStatusCode.Created
}