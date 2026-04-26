package com.orka.myfinances.data.api.debt

import com.orka.myfinances.data.api.debt.models.response.DebtApiModel
import com.orka.myfinances.lib.data.api.map
import com.orka.myfinances.lib.data.models.ChunkApiModel
import com.orka.myfinances.lib.viewmodel.Chunk
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend fun DebtApi.getChunk(
    size: Int,
    page: Int,
    completed: Boolean,
    orderBy: String? = null
): Chunk<DebtApiModel>? {
    val response = httpClient.get(
        urlString = baseUrl,
        block = {
            parameter("branch", officeId.value)
            parameter("is_completed", completed)
            parameter("page_size", size)
            parameter("page", page)
            if(orderBy != null) parameter("ordering", orderBy)
        }
    )
    return if (response.status == HttpStatusCode.OK) response.body<ChunkApiModel<DebtApiModel>>().map() else null
}