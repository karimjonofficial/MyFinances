package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.lib.data.http.getById
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.http.insert
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient

class ReceiveApi(
    val httpClient: HttpClient,
    val baseUrl: String = "receives/"
) {
    suspend fun getChunk(
        branchId: Int,
        page: Int,
        pageSize: Int,
        search: String? = null,
        ordering: String? = null,
        dateTimeFrom: String? = null,
        dateTimeTo: String? = null,
        user: Int? = null
    ): ChunkApiModel<ReceiveApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            ordering = ordering,
            extraParameters = mapOf(
                "branch" to branchId,
                "date_time_from" to dateTimeFrom,
                "date_time_to" to dateTimeTo,
                "user" to user
            )
        )
    }

    suspend fun getById(id: Int): ReceiveApiModel? {
        return httpClient.getById(baseUrl, id)
    }

    suspend inline fun <reified T> insert(request: T): Boolean {
        return httpClient.insert(baseUrl, request)
    }
}
