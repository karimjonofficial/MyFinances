package com.orka.myfinances.data.api.office

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class OfficeApi(private val client: HttpClient) {
    suspend fun get(companyId: Int): List<OfficeApiModel>? {
        val response = client.get(
            urlString = "branches/",
            block = { parameter(key = "company", value = companyId) }
        )
        return if (response.status == HttpStatusCode.OK)
            response.body()
        else null
    }

    suspend fun getById(id: Int): OfficeApiModel? {
        val response: OfficeApiModel? = client.get("branches/$id/").body()
        return response
    }
}