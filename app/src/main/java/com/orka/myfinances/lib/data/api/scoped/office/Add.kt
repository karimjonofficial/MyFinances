package com.orka.myfinances.lib.data.api.scoped.office

import com.orka.myfinances.data.models.Id
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T, reified Request, reified Response> OfficeScopedApi.add(
    request: T,
    map: T.(Id) -> Request
): Response? {
    val apiRequest = request.map(officeId)
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(apiRequest) }
    )
    return response.body()
}

suspend inline fun <reified T> OfficeScopedApi.add(request: T): Boolean {
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(request) }
    )
    val created = response.status == HttpStatusCode.Created
    return created
}