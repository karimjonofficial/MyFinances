package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.data.api.stock.StockApi
import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.data.models.basket.BasketItem
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend fun getBasketItems(minItems: List<MinBasketItem>, api: StockApi): List<BasketItem> {
    val items = minItems.map { minItem ->
        val stockItem = api.httpClient.getStockItem(minItem.id.value)
        if(stockItem != null) {
            BasketItem(
                product = stockItem.product,
                availableAmount = stockItem.amount,
                amount = minItem.amount,
                increaseEnabled = minItem.amount < stockItem.amount,
                decreaseEnabled = true
            )
        } else throw Exception()
    }

    return items
}

private suspend fun HttpClient.getStockItem(id: Int): StockItemApiModel? {
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