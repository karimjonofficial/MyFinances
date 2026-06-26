package com.orka.myfinances.data.api.client

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.lib.data.http.getById
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.http.insert
import com.orka.myfinances.lib.data.http.update
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient

class ClientApi(
    val httpClient: HttpClient,
    val baseUrl: String = "clients/"
) {
    suspend fun getChunk(
        companyId: Int,
        page: Int,
        pageSize: Int,
        search: String? = null,
        ordering: String? = null,
        firstName: String? = null,
        lastName: String? = null,
        phone: String? = null
    ): ChunkApiModel<ClientApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            ordering = ordering,
            extraParameters = mapOf(
                "company" to companyId,
                "first_name" to firstName,
                "last_name" to lastName,
                "phone" to phone
            )
        )
    }

    suspend fun getById(id: Int): ClientApiModel? {
        return httpClient.getById(baseUrl, id)
    }

    suspend inline fun <reified T> insert(request: T): Boolean {
        return httpClient.insert(baseUrl, request)
    }

    suspend inline fun <reified T> update(id: Int, request: T): Boolean {
        return httpClient.update(baseUrl, id, request)
    }
}
