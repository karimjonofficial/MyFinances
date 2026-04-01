package com.orka.myfinances.data.api.client

import com.orka.myfinances.data.api.client.models.response.ClientApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.api.scoped.company.CompanyScopedApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class ClientApi(
    override val httpClient: HttpClient,
    override val companyId: Id,
    override val baseUrl: String = "clients/"
) : CompanyScopedApi {
    suspend fun getById(id: Int): ClientApiModel? {
        val response = httpClient.get("$baseUrl$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddClientRequest): Boolean {
        val response = httpClient.post(
            urlString = baseUrl,
            block = { setBody(request.map(companyId.value)) }
        )
        return response.status == HttpStatusCode.Created
    }
}
