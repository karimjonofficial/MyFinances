package com.orka.myfinances.data.repositories.basket

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.testFixtures.resources.amount
import com.orka.myfinances.testFixtures.resources.models.id1
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BasketRepositoryTest : MainDispatcherContext() {
    private val repository = BasketRepository()

    @Test
    fun `Returns an empty list`() = runTest {
        val items = repository.get()
        assertTrue(items.isEmpty())
    }

    @Test
    fun `Add new item emits FullRefresh`() = runTest {
        val job = launch {
            val event = repository.events.first()
            assertEquals(BasketEvent.FullRefresh, event)
        }
        repository.add(id1, amount)
        job.join()
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
            assertNotNull(items.find { it.id == id1 })
            assertEquals(1, items.size)
        }

        @Test
        fun `Nothing happens when item does not exist`() = runTest {
            advanceUntilIdle()
            val id2 = Id(999)
            val old = repository.get().toList()
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
                val item = items.find { it.id == id1 }
                assertNotNull(item)
                assertEquals(1, items.size)
                assertEquals(2 * amount, item?.amount)
            }

            @Test
            fun `Add existing item emits AmountChanged`() = runTest {
                advanceUntilIdle()
                val job = launch {
                    val event = repository.events.first()
                    assertTrue(event is BasketEvent.AmountChanged)
                    assertEquals(id1, (event as BasketEvent.AmountChanged).id)
                    assertEquals(3 * amount, event.newAmount)
                }
                repository.add(id1, amount)
                job.join()
            }

            @Test
            fun `Remove decreases when item already exists`() = runTest {
                advanceUntilIdle()
                repository.remove(id1, amount)
                val items = repository.get()
                val item = items.find { it.id == id1 }
                assertNotNull(item)
                assertEquals(1, items.size)
                assertEquals(amount, item?.amount)
            }
        }

        @Test
        fun `Remove removes when item already exists and amount is exceeded`() = runTest {
            advanceUntilIdle()
            repository.remove(id1, amount)
            val items = repository.get()
            assertTrue(items.find { it.id == id1 } == null)
        }

        @Test
        fun `Remove existing item emits ItemRemoved when amount reaches zero`() = runTest {
            advanceUntilIdle()
            val job = launch {
                val event = repository.events.first()
                assertTrue(event is BasketEvent.ItemRemoved)
                assertEquals(id1, (event as BasketEvent.ItemRemoved).id)
            }
            repository.remove(id1, amount)
            job.join()
        }
    }
}
