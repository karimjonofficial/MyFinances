package com.orka.myfinances.lib.data.http

import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpClient.getChunk(
    baseUrl: String,
    page: Int,
    pageSize: Int,
    search: String? = null,
    ordering: String? = null,
    extraParameters: Map<String, Any?> = emptyMap()
): ChunkApiModel<T>? {
    val response = get(baseUrl) {
        parameter("page", page)
        parameter("page_size", pageSize)
        if (search != null) parameter("search", search)
        if (ordering != null) parameter("ordering", ordering)
        extraParameters.forEach { (key, value) ->
            if (value != null) {
                if (value is Iterable<*>) {
                    value.forEach { parameter(key, it) }
                } else {
                    parameter(key, value)
                }
            }
        }
    }
    return if (response.status == HttpStatusCode.OK) response.body() else null
}
