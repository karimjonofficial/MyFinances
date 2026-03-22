package com.orka.myfinances.lib.data.api

import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T, reified R> Api.add(
    request: T,
    map: T.() -> R,
    onSuccess: suspend (R) -> Unit
): Boolean {
    val apiRequest = request.map()
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(apiRequest) }
    )
    val created = response.status == HttpStatusCode.Created
    if (created) onSuccess(apiRequest)
    return created
}