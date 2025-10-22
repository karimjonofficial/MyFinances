package com.orka.myfinances.data.repositories.product

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.data.api.SpyProductApiService
import com.orka.myfinances.testLib.addProductRequest
import com.orka.myfinances.testLib.product
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductRepositoryTest : MainDispatcherContext() {

    @Test
    fun `When add, returns what api returns`() = testScope.runTest {
        val apiService = SpyProductApiService()
        val repository = ProductRepository(apiService)
        val result = repository.add(addProductRequest)
        assertTrue { product === result }
    }
}