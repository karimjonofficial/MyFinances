package com.orka.myfinances.data.api.stock

import com.orka.myfinances.core.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class StockApi(private val client: HttpClient) {
    suspend fun getByCategory(
        categoryId: Int,
        officeId: Int,
        token: String
    ): List<StockItemModel>? {
        return try {
            client.get("stock/category=$categoryId") {
                parameter("officeId", officeId)
                header("Authorization", "Bearer $token")
            }.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    companion object {
        fun create(): StockApi {
            val client = HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    })
                }

                install(HttpTimeout) {
                    requestTimeoutMillis = 10000
                    connectTimeoutMillis = 10000
                    socketTimeoutMillis = 10000
                }

                defaultRequest {
                    url(urlString = baseUrl)
                }
            }
            return StockApi(client)
        }
    }
}
