package com.orka.myfinances.data.api.title

import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.api.map
import com.orka.myfinances.lib.data.models.ChunkApiModel
import com.orka.myfinances.lib.viewmodel.Chunk
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

suspend fun ProductTitleApi.getByCategory(
    size: Int,
    pageIndex: Int,
    categoryId: Id
): Chunk<ProductTitleApiModel>? {
    val response = httpClient.get(
        urlString = "product-titles/",
        block = {
            parameter("category", categoryId.value)
            parameter("page_size", size)
            parameter("page", pageIndex)
            parameter("ordering", "name")
        }
    )
    return if (response.status == HttpStatusCode.OK) response.body<ChunkApiModel<ProductTitleApiModel>>().map() else null
}