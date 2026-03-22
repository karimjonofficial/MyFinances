package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.api.receive.models.response.ReceiveApiModel
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableSharedFlow

class ReceiveApi(
    private val client: HttpClient,
    private val office: Office,
    private val flow: MutableSharedFlow<ReceiveEvent>
) {
    private val baseUrl = "receives/"

    suspend fun getAll(): List<ReceiveApiModel>? {
        val response = client.get(
            urlString = baseUrl,
            block = { parameter("branch", office.id.value) }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getChunk(size: Int, page: Int): List<ReceiveApiModel>? {
        val response = client.get(
            urlString = baseUrl,
            block = {
                parameter("branch", office.id.value)
                parameter("page_size", size)
                parameter("page", page)
            }
        )
        return if (response.status == HttpStatusCode.OK) response.body<ChunkApiModel<ReceiveApiModel>>().results else null
    }

    suspend fun getById(id: Int): ReceiveApiModel? {
        val response = client.get("$baseUrl$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddReceiveRequest): Boolean {
        val response = client.post(
            urlString = baseUrl,
            block = { setBody(request.map(office.id.value)) }
        )
        val created = response.status == HttpStatusCode.Created
        if (created) flow.emit(ReceiveEvent)
        return created
    }
}