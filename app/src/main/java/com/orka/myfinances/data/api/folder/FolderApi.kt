package com.orka.myfinances.data.api.folder

import com.orka.myfinances.data.api.folder.models.request.AddFolderApiRequest
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.lib.data.http.add
import com.orka.myfinances.lib.data.http.getById
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class FolderApi(
    private val httpClient: HttpClient,
    private val baseUrl: String = "categories/",
) {
    suspend fun get(
        officeId: Int,
        isCatalog: Boolean? = null,
        name: String? = null,
        ordering: String? = null,
        parent: String? = null,
        search: String? = null
    ): List<FolderApiModel>? {
        val response = httpClient.get(baseUrl) {
            parameter("branch", officeId)
            isCatalog?.let { parameter("is_catalog", it) }
            name?.let { parameter("name", it) }
            ordering?.let { parameter("ordering", it) }
            parent?.let { parameter("parent", it) }
            search?.let { parameter("search", it) }
        }
        return if (response.status == HttpStatusCode.OK)
            response.body()
        else null
    }

    suspend fun getByParent(
        officeId: Int,
        parentId: Int,
        search: String? = null
    ): List<FolderApiModel>? {
        return get(
            officeId = officeId,
            parent = parentId.toString(),
            ordering = "name",
            search = search
        )
    }

    suspend fun getTop(
        officeId: Int,
        search: String? = null
    ): List<FolderApiModel>? {
        return get(
            officeId = officeId,
            parent = "null",
            ordering = "name",
            search = search
        )
    }

    suspend fun getById(id: Int): FolderApiModel? {
        return httpClient.getById(baseUrl, id)
    }

    suspend fun add(request: AddFolderApiRequest): FolderApiModel? {
        return httpClient.add(
            baseUrl = baseUrl,
            request = request
        )
    }
}