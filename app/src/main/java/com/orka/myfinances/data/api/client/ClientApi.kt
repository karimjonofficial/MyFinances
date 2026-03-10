package com.orka.myfinances.data.api.client

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class ClientApi(private val client: HttpClient) {
    suspend fun getAll(): List<ClientApiModel>? {
        val response = client.get("clients/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): ClientApiModel? {
        val response = client.get("clients/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddClientApiRequest): Boolean {
        val response = client.post(
            urlString = "clients/",
            block = { setBody(request) }
        )
        return response.status == HttpStatusCode.Created
    }
}
