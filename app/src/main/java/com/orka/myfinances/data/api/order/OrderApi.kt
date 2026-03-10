package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class OrderApi(
    private val client: HttpClient,
    private val office: Office
) {
    suspend fun getAll(): List<OrderApiModel>? {
        val response = client.get("orders/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): OrderApiModel? {
        val response = client.get("orders/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddOrderRequest): Boolean {
        val response = client.post(
            urlString = "orders/",
            block = { setBody(request.map(office.id)) }
        )
        return response.status == HttpStatusCode.Created
    }
}