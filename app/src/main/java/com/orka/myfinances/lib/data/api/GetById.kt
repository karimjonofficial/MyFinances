package com.orka.myfinances.lib.data.api

import com.orka.myfinances.data.models.Id
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> Api<T>.getById(id: Id): T? {
    val response = httpClient.get("$baseUrl${id.value}/")
    return if (response.status == HttpStatusCode.OK) response.body<T>() else null
}