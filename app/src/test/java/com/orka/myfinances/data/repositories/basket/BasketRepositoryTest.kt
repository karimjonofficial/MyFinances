package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.data.repositories.product.ProductTitleRepository
import com.orka.myfinances.testFixtures.resources.amount
import com.orka.myfinances.testFixtures.resources.models.id1
import com.orka.myfinances.testFixtures.resources.models.product.product2
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketRepositoryTest : MainDispatcherContext() {
    private val productRepository = ProductRepository(ProductTitleRepository())
    private val repository = BasketRepository(productRepository)

    @Test
    fun `Returns an empty list`() {
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
            assertNotNull(items.find { it.product.id == id1 })
            assertEquals(1, items.size)
        }

        @Test
        fun `Nothing happens when item does not exist`() = runTest {
            advanceUntilIdle()
            val old = repository.get()
            repository.remove(product2.id, amount)
            val new = repository.get()
            assertEquals(old, new)
        }

        @Nested
        inner class AddProduct1Context {
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
                    items.find { it.product.id == id1 } != null
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