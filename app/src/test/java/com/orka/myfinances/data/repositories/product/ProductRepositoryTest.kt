package com.orka.myfinances.data.repositories.product

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.testFixtures.resources.failingAddProductRequest
import com.orka.myfinances.testFixtures.resources.models.product.product1
import com.orka.myfinances.testFixtures.resources.successfulAddProductRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductRepositoryTest : MainDispatcherContext() {
    private val repository = ProductRepository(ProductTitleRepository())

    @Test
    fun `When a product is added, the products flow is updated`() = runTest {
        repository.events().test {
            repository.add(successfulAddProductRequest)
            assertEquals(ProductRepositoryEvent.Add(product1.id), awaitItem())
        }
    }

    @Test
    fun `When a product is not added, the products flow is not updated`() = runTest {
        repository.add(failingAddProductRequest)
        advanceUntilIdle()

        repository.events().test {
            expectNoEvents()
        }
    }
}