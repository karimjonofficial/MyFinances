package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.data.api.scoped.office.OfficeScopedApi
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class DebtApi(
    override val httpClient: HttpClient,
    override val office: Office,
    override val baseUrl: String = "debts/"
) : OfficeScopedApi {
    suspend fun getById(id: Int): DebtApiModel? {
        val response = httpClient.get("$baseUrl$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddDebtRequest): Boolean {
        val response = httpClient.post(
            urlString = baseUrl,
            block = { setBody(request) }
        )
        return response.status == HttpStatusCode.Created
    }
}
