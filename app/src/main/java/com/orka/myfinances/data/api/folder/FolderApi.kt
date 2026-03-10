package com.orka.myfinances.data.api.folder

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class FolderApi(private val client: HttpClient) {
    suspend fun getByOffice(officeId: Int): List<FolderApiModel>? {
        val response = client.get(
            urlString = "categories/",
            block = { parameter("branch", officeId) }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getByParent(parentId: Int, officeId: Int): List<FolderApiModel>? {
        val response = client.get(
            urlString = "categories/",
            block = {
                parameter("parent_id", parentId)
                parameter("branch", officeId)
            }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): FolderApiModel? {
        val response = client.get("categories/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddFolderApiRequest): FolderApiModel? {
        val response = client.post(
            urlString = "categories/",
            block = { setBody(request) }
        )
        return if (response.status == HttpStatusCode.Created) response.body() else null
    }
}
