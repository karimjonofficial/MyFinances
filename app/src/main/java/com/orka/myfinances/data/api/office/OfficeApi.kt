package com.orka.myfinances.data.api.office

import com.orka.myfinances.lib.data.http.getById
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class OfficeApi(
    val httpClient: HttpClient,
    val baseUrl: String = "branches/"
) {
    suspend fun get(
        companyId: Int,
        search: String? = null
    ): List<OfficeApiModel>? {
        val response = httpClient.get(baseUrl) {
            parameter("company", companyId)
            if (search != null) parameter("search", search)
        }
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getChunk(
        companyId: Int,
        page: Int,
        pageSize: Int,
        search: String? = null
    ): ChunkApiModel<OfficeApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            extraParameters = mapOf("company" to companyId)
        )
    }

    suspend fun getById(id: Int): OfficeApiModel? {
        return httpClient.getById(baseUrl, id)
    }
}
