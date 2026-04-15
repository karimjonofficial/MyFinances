package com.orka.myfinances.lib.data.api.scoped.office

import com.orka.myfinances.data.models.Id
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

suspend inline fun <reified T, reified Request, reified Response> OfficeScopedApi<Response>.add(
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