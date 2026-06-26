package com.orka.myfinances.lib.data.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

suspend inline fun <reified T, reified R> HttpClient.add(
    baseUrl: String,
    request: T
): R? {
    val response = post(
        urlString = baseUrl,
        block = { setBody(request) }
    )
    return response.body()
}