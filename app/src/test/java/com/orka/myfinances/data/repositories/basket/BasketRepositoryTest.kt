package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.testFixtures.resources.amount
import com.orka.myfinances.testFixtures.resources.models.id1
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketRepositoryTest : MainDispatcherContext() {
    private val mockEngine = MockEngine { request: HttpRequestData ->
        val id = request.url.encodedPath.split("/").last { it.isNotEmpty() }.toInt()
        respond(
            content = """
                {
                    "id": $id,
                    "product_title": {
                        "id": 1,
                        "category": 1,
                        "name": "Product $id",
                        "properties": [],
                        "default_price": 100,
                        "default_sale_price": 110,
                        "default_exposed_price": 120,
                        "created_at": "2024-01-01T12:00:00Z",
                        "modified_at": "2024-01-01T12:00:00Z"
                    },
                    "price": 100,
                    "sale_price": 110,
                    "exposed_price": 120,
                    "created_at": "2024-01-01T12:00:00Z",
                    "modified_at": "2024-01-01T12:00:00Z"
                }
            """.trimIndent(),
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
        )
    }

    private val httpClient = HttpClient(mockEngine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val repository = BasketRepository(httpClient)

    @Test
    fun `Returns an empty list`() = runTest {
        val items = repository.get()
        assertTrue(items.isEmpty())
    }

    @Nested
    inner class AddProduct1Context {
        @BeforeEach
        fun setup() {
            testScope.launch {
                repository.add(id1, amount)
            }
        }

        @Test
        fun `Add items adds items`() = runTest {
            advanceUntilIdle()
            val items = repository.get()
            assertNotNull(items.find { it.product.id == id1.value })
            assertEquals(1, items.size)
        }

        @Test
        fun `Nothing happens when item does not exist`() = runTest {
            advanceUntilIdle()
            val id2 = Id(999)
            val old = repository.get()
            repository.remove(id2, amount)
            val new = repository.get()
            assertEquals(old, new)
        }

        @Nested
        inner class AddAgainContext {
            @BeforeEach
            fun setup() {
                advanceUntilIdle()
                testScope.launch {
                    repository.add(id1, amount)
                }
            }

            @Test
            fun `Add increases when item already exists`() = runTest {
                advanceUntilIdle()
                val items = repository.get()
                assertTrue(
                    items.find { it.product.id == id1.value } != null
                            && items.size == 1
                            && items[0].amount == 2 * amount
                )
            }

            @Test
            fun `Remove decreases when item already exists`() = runTest {
                advanceUntilIdle()
                repository.remove(id1, amount)
                val items = repository.get()
                assertTrue(
                    items.find { it.product.id == id1.value } != null
                            && items.size == 1
                            && items[0].amount == amount
                )
            }
        }

        @Test
        fun `Remove removes when item already exists and amount is exceeded`() = runTest {
            advanceUntilIdle()
            repository.remove(id1, amount)
            val items = repository.get()
            assertTrue(items.find { it.product.id == id1.value } == null)
        }
    }
}
