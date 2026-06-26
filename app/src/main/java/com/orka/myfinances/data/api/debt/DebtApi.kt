package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.api.debt.models.response.DebtApiModel
import com.orka.myfinances.lib.data.http.getById
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.http.insert
import com.orka.myfinances.lib.data.http.patch
import com.orka.myfinances.lib.data.http.update
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient

class DebtApi(
    val httpClient: HttpClient,
    val baseUrl: String = "debts/"
) {
    suspend fun getChunk(
        officeId: Int,
        page: Int,
        pageSize: Int,
        search: String? = null,
        ordering: String? = null,
        client: Int? = null,
        description: String? = null,
        endDateTimeFrom: String? = null,
        endDateTimeTo: String? = null,
        isCompleted: Boolean? = null,
        notified: Boolean? = null,
        user: Int? = null
    ): ChunkApiModel<DebtApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            ordering = ordering,
            extraParameters = mapOf(
                "branch" to officeId,
                "client" to client,
                "description" to description,
                "end_date_time_from" to endDateTimeFrom,
                "end_date_time_to" to endDateTimeTo,
                "is_completed" to isCompleted,
                "notified" to notified,
                "user" to user
            )
        )
    }

    suspend fun getById(id: Int): DebtApiModel? {
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
