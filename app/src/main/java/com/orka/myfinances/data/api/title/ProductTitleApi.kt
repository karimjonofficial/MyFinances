package com.orka.myfinances.data.api.title

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class ProductTitleApi(private val client: HttpClient) {
    suspend fun getByCategory(categoryId: Int): List<ProductTitleApiModel>? {
        val response = client.get(
            urlString = "product-titles/",
            block = { parameter("category", categoryId) }
        )
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun getById(id: Int): ProductTitleApiModel? {
        val response = client.get("product-titles/$id/")
        return if (response.status == HttpStatusCode.OK) response.body() else null
    }

    suspend fun add(request: AddProductTitleApiRequest): Boolean {
        val response = client.post(
            urlString = "product-titles/",
            block = { setBody(request) }
        )
        return response.status == HttpStatusCode.Created
    }
}
