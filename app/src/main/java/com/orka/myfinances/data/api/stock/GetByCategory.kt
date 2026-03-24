package com.orka.myfinances.data.api.stock

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.map
import com.orka.myfinances.lib.data.models.ChunkApiModel
import com.orka.myfinances.lib.viewmodel.Chunk
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend fun StockApi1.getByCategory(
    size: Int,
    pageIndex: Int,
    categoryId: Id
): Chunk<StockItemApiModel>? {
    val response = httpClient.get(
        urlString = "stock-items/",
        block = {
            parameter("branch", office.id.value)
            parameter("category", categoryId.value)
            parameter("page_size", size)
            parameter("page", pageIndex)
        }
    )
    return if (response.status == HttpStatusCode.OK) response.body<ChunkApiModel<StockItemApiModel>>().map() else null
}
