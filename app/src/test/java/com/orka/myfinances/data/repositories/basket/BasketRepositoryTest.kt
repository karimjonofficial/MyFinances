package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.data.api.product.ProductApiServiceStub
import com.orka.myfinances.testLib.amount
import com.orka.myfinances.testLib.id1
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasketRepositoryTest : MainDispatcherContext() {
    private val apiService = ProductApiServiceStub()
    private val productRepository = ProductRepository(apiService)
    private val repository = BasketRepository(productRepository)

    @Test
    fun `Returns an empty basket`() {
        val basket = repository.get()
        assertTrue { basket.isEmpty() }
    }

    @Test
    fun `Add items adds items`() = testScope.runTest {
        repository.add(id1, amount)
        val items = repository.get()
        assertTrue { items.find { it.product.id== id1 } != null && items.size == 1 }
    }

    @Test
    fun `Add increases when item already exists`() = testScope.runTest {
        repository.add(id1, amount)
        repository.add(id1, amount)
        val items = repository.get()
        assertTrue {
            items.find { it.product.id == id1 } != null
                    && items.size == 1
                    && items[0].amount == 2
        }
    }
}