package com.orka.myfinances.data.repositories.product

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.testLib.addProductRequest
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.testLib.products
import com.orka.myfinances.fixtures.data.api.product.ProductApiServiceStub
import com.orka.myfinances.testLib.id1
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductRepositoryTest : MainDispatcherContext() {

    @Test
    fun `Add returns what api returns`() = testScope.runTest {
        val apiService = SpyProductApiService()
        val repository = ProductRepository(apiService)
        val result = repository.add(addProductRequest)
        assertTrue(product1 === result)
    }

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
    fun `Get returns what api returns`() = testScope.runTest {
        val apiService = SpyProductApiService()
        val repository = ProductRepository(apiService)
        val response = repository.get()
        assertEquals(response, products)
    }

    @Test
    fun `Get by warehouse id returns what api returns`() = testScope.runTest {
        val apiService = SpyProductApiService()
        val repository = ProductRepository(apiService)
        val response = repository.get(id1)
        assertEquals(response, products)
    }

    @Test
    fun `Get by id returns what api returns`() = testScope.runTest {
        val apiService = SpyProductApiService()
        val repository = ProductRepository(apiService)
        val response = repository.getById(id1)
        assertEquals(response, product1)
    }
}