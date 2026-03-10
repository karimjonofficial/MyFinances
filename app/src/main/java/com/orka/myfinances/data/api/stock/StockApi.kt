package com.orka.myfinances.data.api.stock

import com.orka.myfinances.data.models.Office
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class StockApi(
    private val client: HttpClient,
    private val office: Office
) {
    suspend fun getByCategory(categoryId: Int): List<StockItemApiModel>? {
        val response = client.get(
            urlString = "stock-items",
            block = {
                parameter("category", categoryId)
                parameter("branch", office.id.value)
            }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }
}
