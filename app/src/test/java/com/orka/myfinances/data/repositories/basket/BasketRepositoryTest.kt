package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.data.api.product.ProductApiServiceStub
import com.orka.myfinances.testLib.amount
import com.orka.myfinances.testLib.id1
import com.orka.myfinances.testLib.product2
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketRepositoryTest : MainDispatcherContext() {
    private val apiService = ProductApiServiceStub()
    private val productRepository = ProductRepository(apiService)
    private val repository = BasketRepository(productRepository)

    @Test
    fun `Returns an empty basket`() {
        val items = repository.get()
        assertTrue(items.isEmpty())
    }

    @Nested
    inner class AddProduct1Context {
        @BeforeEach
        fun setup() {
            testScope.launch {
                repository.add(id1, amount)
                advanceUntilIdle()
            }
        }

        @Test
        fun `Add items adds items`() = runTest {
            val items = repository.get()
            assertTrue(items.find { it.product.id == id1 } != null && items.size == 1)
        }

        @Test
        fun `Nothing happens when item does not exist`() {
            val old = repository.get()
            repository.remove(product2.id, amount)
            val new = repository.get()
            assertEquals(old, new)
        }

        @Nested
        inner class AddProduct1Context {
            @BeforeEach
            fun setup() {
                testScope.launch {
                    repository.add(id1, amount)
                    advanceUntilIdle()
                }
            }

            @Test
            fun `Add increases when item already exists`() = runTest {
                val items = repository.get()
                assertTrue(
                    items.find { it.product.id == id1 } != null
                            && items.size == 1
                            && items[0].amount == 2 * amount
                )
            }

            @Test
            fun `Remove decreases when item already exists`() = runTest {
                repository.remove(id1, amount)
                val items = repository.get()
                assertTrue(
                    items.find { it.product.id == id1 } != null
                            && items.size == 1
                            && items[0].amount == amount
                )
            }
        }

        @Test
        fun `Remove removes when item already exists and amount is 1`() = runTest {
            repository.remove(id1, amount)
            val items = repository.get()
            assertTrue(items.find { it.product.id == id1 } == null)
        }
    }
}