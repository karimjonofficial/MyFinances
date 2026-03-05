package com.orka.myfinances.data.api.folder

import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class FolderApi(private val client: HttpClient) {
    suspend fun get(branch: Int): List<FolderModel>? {
        return client.get(
            urlString = "categories/",
            block = { parameter(key = "branch", value = branch) }
        ).body()
    }

    suspend fun add(request: AddFolderRequest): FolderModel? {
        return client.post(
            urlString = "categories/",
            block = { setBody(request.map()) }
        ).body()
    }

    suspend fun getChildren(id: Int, branch: Int): List<FolderModel>? {
        return client.get(
            urlString = "categories/",
            block = {
                parameter("parent_id", id)
                parameter("branch", branch)
            }
        ).body()
    }

    suspend fun getTop(branch: Int): List<FolderModel>? {
        return client.get(
            urlString = "categories/",
            block = { parameter(key = "branch", branch) }
        ).body()
    }
}