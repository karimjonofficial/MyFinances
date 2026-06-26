package com.orka.myfinances.data.api.notification

import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode

class NotificationApi(
    private val httpClient: HttpClient,
    private val baseUrl: String = "notifications/"
) {
    suspend fun getChunk(
        page: Int,
        pageSize: Int,
        search: String? = null
    ): ChunkApiModel<NotificationApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search
        )
    }

    suspend fun read(id: Int): Boolean {
        val response = httpClient.post("$baseUrl$id/read/")
        return response.status == HttpStatusCode.OK
    }
}
