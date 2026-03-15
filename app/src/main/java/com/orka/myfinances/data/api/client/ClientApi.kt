package com.orka.myfinances.data.api.client

import com.orka.myfinances.data.models.Company
import com.orka.myfinances.data.repositories.client.AddClientRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class ClientApi(
    private val client: HttpClient,
    private val company: Company
) {
    suspend fun getAll(): List<ClientApiModel>? {
        val response = client.get(
            urlString = "clients/",
            block = {
                parameter("company", company.id.value)
            }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): ClientApiModel? {
        val response = client.get("clients/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddClientRequest): Boolean {
        val response = client.post(
            urlString = "clients/",
            block = { setBody(request.map(company.id.value)) }
        )
        return response.status == HttpStatusCode.Created
    }
}
