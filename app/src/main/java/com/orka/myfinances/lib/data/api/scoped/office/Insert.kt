package com.orka.myfinances.lib.data.api.scoped.office

import com.orka.myfinances.data.models.Id
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T, reified R> OfficeScopedApi.insert(
    request: T,
    map: T.(Id) -> R
): Boolean {
    val apiRequest = request.map(officeId)
    val response = httpClient.post(
        urlString = baseUrl,
        block = { setBody(apiRequest) }
    )
    val created = response.status == HttpStatusCode.Created
    return created
}