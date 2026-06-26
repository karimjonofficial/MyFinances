package com.orka.myfinances.data.api.template

import com.orka.myfinances.data.api.template.models.request.AddTemplateApiRequest
import com.orka.myfinances.data.api.template.models.response.TemplateApiModel
import com.orka.myfinances.lib.data.http.getById
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.http.insert
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient

class TemplateApi(
    private val httpClient: HttpClient,
    private val baseUrl: String = "templates/"
) {
    suspend fun getChunk(
        page: Int,
        pageSize: Int,
        search: String? = null,
        ordering: String? = null,
        fields: List<Int>? = null,
        name: String? = null
    ): ChunkApiModel<TemplateApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            ordering = ordering,
            extraParameters = mapOf(
                "fields" to fields,
                "name" to name
            )
        )
    }

    suspend fun getById(id: Int): TemplateApiModel? {
        return httpClient.getById(baseUrl, id)
    }

    suspend fun insert(request: AddTemplateApiRequest): Boolean {
        return httpClient.insert(baseUrl, request)
    }
}
