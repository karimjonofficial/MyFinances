package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend fun HttpClient.getStockItem(id: Int): StockItemApiModel? {
    val response = get(
        urlString = "stock-items/",
        block = { parameter("product", id) }
    )

    return if(response.status == HttpStatusCode.OK) {
        val items = response.body<ChunkApiModel<StockItemApiModel>>()
        if(items.results.size == 1) items.results[0]
        else null
    } else null
}