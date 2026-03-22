package com.orka.myfinances.data.api.sale

import com.orka.myfinances.data.api.sale.models.response.SaleApiModel
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.data.repositories.sale.SaleEvent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableSharedFlow

class SaleApi(
    private val client: HttpClient,
    private val office: Office,
    private val flow: MutableSharedFlow<SaleEvent>
) {
    suspend fun getAll(): List<SaleApiModel>? {
        val response = client.get(
            urlString = "sales/",
            block = { parameter("branch", office.id.value) }
        )
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
        val created = response.status == HttpStatusCode.Created
        if (created) flow.emit(SaleEvent)
        return if (created) response.body() else null
    }
}