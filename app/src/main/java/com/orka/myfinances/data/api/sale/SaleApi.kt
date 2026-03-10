package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class SaleApi(
    private val client: HttpClient,
    private val office: Office
) {
    suspend fun getAll(): List<SaleApiModel>? {
        val response = client.get("sales/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): SaleApiModel? {
        val response = client.get("sales/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddSaleRequest): SaleApiModel? {
        val response = client.post(
            urlString = "sales/",
            block = { setBody(request.map(office.id.value)) }
        )
        return if (response.status == HttpStatusCode.Created) response.body() else null
    }
}
