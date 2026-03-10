package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class DebtApi(private val client: HttpClient) {
    suspend fun getAll(): List<DebtApiModel>? {
        val response = client.get("merchant/debts/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): DebtApiModel? {
        val response = client.get("merchant/debts/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddDebtRequest): Boolean {
        val response = client.post(
            urlString = "merchant/debts/",
            block = { setBody(request) }
        )
        return response.status == HttpStatusCode.Created
    }
}
