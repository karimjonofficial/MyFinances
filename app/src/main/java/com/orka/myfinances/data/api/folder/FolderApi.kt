package com.orka.myfinances.data.api.folder

import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableSharedFlow

class FolderApi(
    private val client: HttpClient,
    private val office: Office,
    private val flow: MutableSharedFlow<FolderEvent>
) {
    suspend fun getTop(): List<FolderApiModel>? {
        val response = client.get(
            urlString = "categories/",
            block = {
                parameter("branch", office.id.value)
                parameter("parent", "null")
                parameter("ordering", "name")
            }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getByParent(parentId: Int): List<FolderApiModel>? {
        val response = client.get(
            urlString = "categories/",
            block = {
                parameter("parent", parentId)
                parameter("branch", office.id.value)
                parameter("ordering", "name")
            }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): FolderApiModel? {
        val response = client.get("categories/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddFolderRequest) {
        val response = client.post(
            urlString = "categories/",
            block = { setBody(request.toApiRequest(office.id.value)) }
        )
        val created = response.status == HttpStatusCode.Created
        if (created) flow.emit(FolderEvent(request.parentId))
    }
}