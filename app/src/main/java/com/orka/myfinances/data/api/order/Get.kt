package com.orka.myfinances.data.api.order

import com.orka.myfinances.data.api.order.models.response.OrderApiModel
import com.orka.myfinances.lib.data.api.map
import com.orka.myfinances.lib.data.models.ChunkApiModel
import com.orka.myfinances.lib.viewmodel.Chunk
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend fun OrderApi.getChunk(
    size: Int,
    page: Int,
    completed: Boolean,
    orderBy: String? = null,
): Chunk<OrderApiModel>? {
    val response = httpClient.get(
        urlString = baseUrl,
        block = {
            parameter("branch", officeId.value)
            parameter("page_size", size)
            parameter("page", page)
            parameter("completed", completed)
            if(orderBy != null) parameter("ordering", orderBy)
        }
    )
    return if (response.status == HttpStatusCode.OK) response.body<ChunkApiModel<OrderApiModel>>().map() else null
}