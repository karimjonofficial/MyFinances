package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.lib.data.http.getById
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.http.insert
import com.orka.myfinances.lib.data.http.patch
import com.orka.myfinances.lib.data.http.update
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient

class OrderApi(
    val httpClient: HttpClient,
    val baseUrl: String = "orders/"
) {
    suspend fun getChunk(
        branchId: Int,
        page: Int,
        pageSize: Int,
        search: String? = null,
        ordering: String? = null,
        client: Int? = null,
        completed: Boolean? = null,
        description: String? = null,
        endDateTimeFrom: String? = null,
        endDateTimeTo: String? = null,
        user: Int? = null
    ): ChunkApiModel<OrderApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            ordering = ordering,
            extraParameters = mapOf(
                "branch" to branchId,
                "client" to client,
                "completed" to completed,
                "description" to description,
                "end_date_time_from" to endDateTimeFrom,
                "end_date_time_to" to endDateTimeTo,
                "user" to user
            )
        )
    }

    suspend fun getById(id: Int): OrderApiModel? {
        return httpClient.getById(baseUrl, id)
    }

    suspend inline fun <reified T> insert(request: T): Boolean {
        return httpClient.insert(baseUrl, request)
    }

    suspend inline fun <reified T> update(id: Int, request: T): Boolean {
        return httpClient.update(baseUrl, id, request)
    }

    suspend inline fun <reified T> patch(id: Int, request: T): Boolean {
        return httpClient.patch(baseUrl, id, request)
    }
}
