package com.orka.myfinances.data.api.office

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.scoped.company.CompanyScopedApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class OfficeApi(
    override val companyId: Id,
    override val httpClient: HttpClient,
    override val baseUrl: String = "branches/"
) : CompanyScopedApi {
    suspend fun get(): List<OfficeApiModel>? {
        val response = httpClient.get(
            urlString = baseUrl,
            block = { parameter(key = "company", value = companyId.value) }
        )
        return if (response.status == HttpStatusCode.OK)
            response.body()
        else null
    }

    suspend fun getById(id: Int): OfficeApiModel? {
        val response: OfficeApiModel? = httpClient.get("$baseUrl/$id/").body()
        return response
    }
}