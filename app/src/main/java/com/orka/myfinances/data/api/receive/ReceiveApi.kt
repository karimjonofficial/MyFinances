package com.orka.myfinances.data.api.receive

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class ReceiveApi(
    private val client: HttpClient,
    private val office: Office
) {
    suspend fun getAll(): List<ReceiveApiModel>? {
        val response = client.get("receives/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): ReceiveApiModel? {
        val response = client.get("receives/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddReceiveRequest): Boolean {
        val response = client.post(
            urlString = "receives/",
            block = { setBody(request.map(office.id.value)) }
        )
        return response.status == HttpStatusCode.Created
    }
}