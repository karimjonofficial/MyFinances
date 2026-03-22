package com.orka.myfinances.lib.data.api.scoped.office

import com.orka.myfinances.lib.data.api.map
import com.orka.myfinances.lib.data.models.ChunkApiModel
import com.orka.myfinances.lib.viewmodel.Chunk
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> OfficeScopedApi.getChunk(size: Int, page: Int): Chunk<T>? {
    val response = httpClient.get(
        urlString = baseUrl,
        block = {
            parameter("branch", office.id.value)
            parameter("page_size", size)
            parameter("page", page)
        }
    )
    return if (response.status == HttpStatusCode.OK) response.body<ChunkApiModel<T>>().map() else null
}