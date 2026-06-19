package com.orka.myfinances.lib.data.api.scoped.company

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> CompanyScopedApi<T>.getAll(search: String? = null): List<T>? {
    val response = httpClient.get(
        urlString = baseUrl,
        block = {
            parameter("company", companyId.value)
            if (search != null) parameter("search", search)
        }
    )
    return if (response.status == HttpStatusCode.OK) response.body() else null
}