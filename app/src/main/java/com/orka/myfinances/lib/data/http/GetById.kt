package com.orka.myfinances.lib.data.http

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpClient.getById(baseUrl: String, id: Int): T? {
    val response = get("$baseUrl${id}/")
    return if(response.status == HttpStatusCode.OK) response.body<T>() else null
}