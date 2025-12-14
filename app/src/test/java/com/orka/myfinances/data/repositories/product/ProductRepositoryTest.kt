package com.orka.myfinances.data.repositories.product

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.testFixtures.data.api.product.EmptyProductApiServiceStub
import com.orka.myfinances.testFixtures.data.api.product.ProductApiServiceStub
import com.orka.myfinances.testFixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.testFixtures.resources.addProductRequest
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.product.product1
import com.orka.myfinances.testFixtures.resources.models.product.products
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ProductRepositoryTest : MainDispatcherContext() {

    @Test
    fun `When a product is added, the products flow is updated`() = testScope.runTest {
        val apiService = ProductApiServiceStub()
        val repository = ProductRepository(apiService)

        repository.events().test {
            repository.add(addProductRequest)
            assertEquals(ProductRepositoryEvent.Add(product1.id), awaitItem())
        }
    }

    @Test
    fun `When a product is not added, the products flow is not updated`() = testScope.runTest {
        val apiService = EmptyProductApiServiceStub()
        val repository = ProductRepository(apiService)

        repository.events().test {
            repository.add(addProductRequest)
            advanceUntilIdle()
            expectNoEvents()
        }
    }

    @Nested
    inner class SpyApiServiceContext {
        val apiService = SpyProductApiService()
        @Test
        fun `Add returns what api returns`() = testScope.runTest {
            val repository = ProductRepository(apiService)
            val result = repository.add(addProductRequest)
            assertTrue(product1 === result)
        }

        @Test
        fun `Get returns what api returns`() = testScope.runTest {
            val repository = ProductRepository(apiService)
            val response = repository.get()
            assertEquals(response, products)
        }

        @Test
        fun `Get by warehouse id returns what api returns`() = testScope.runTest {
            val repository = ProductRepository(apiService)
            val response = repository.get(id1)
            assertEquals(response, products)
        }

        @Test
        fun `Get by id returns what api returns`() = testScope.runTest {
            val repository = ProductRepository(apiService)
            val response = repository.getById(id1)
            assertEquals(response, product1)
        }
    }
}