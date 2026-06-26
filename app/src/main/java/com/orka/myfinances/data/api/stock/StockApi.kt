package com.orka.myfinances.data.api.stock

import com.orka.myfinances.data.api.stock.models.StockItemApiModel
import com.orka.myfinances.lib.data.http.getChunk
import com.orka.myfinances.lib.data.models.ChunkApiModel
import io.ktor.client.HttpClient

class StockApi(
    private val httpClient: HttpClient,
    private val baseUrl: String = "stock-items/"
) {
    suspend fun getByCategory(
        branchId: Int,
        categoryId: Int,
        page: Int,
        pageSize: Int,
        search: String? = null
    ): ChunkApiModel<StockItemApiModel>? {
        return httpClient.getChunk(
            baseUrl = baseUrl,
            page = page,
            pageSize = pageSize,
            search = search,
            extraParameters = mapOf(
                "branch" to branchId,
                "category" to categoryId
            )
        )
    }

    suspend fun getByProduct(productId: Int): StockItemApiModel? {
        val items = httpClient.getChunk<StockItemApiModel>(
            baseUrl = baseUrl,
            page = 1,
            pageSize = 2,
            extraParameters = mapOf("product" to productId)
        ) ?: return null

        return items.results.singleOrNull()
    }
}
