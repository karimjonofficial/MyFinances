package com.orka.myfinances.data.repositories

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.api.StockApiService
import com.orka.myfinances.data.repositories.stock.StockRepository
import com.orka.myfinances.testFixtures.data.api.warehouse.EmptyStockApiServiceStub
import com.orka.myfinances.testFixtures.data.api.warehouse.StockApiServiceStub
import com.orka.myfinances.testFixtures.resources.models.id1
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class StockRepositoryTest : MainDispatcherContext() {
    private fun repository(apiService: StockApiService): StockRepository {
        return StockRepository(apiService)
    }

    @Test
    fun `When api fails, return null`() = testScope.runTest {
        val repository = repository(EmptyStockApiServiceStub())
        val result = repository.get(id1)
        assertNull(result)
    }

    @Test
    fun `When api successes, return what api returns`() = testScope.runTest {
        val repository = repository(StockApiServiceStub())
        val result = repository.get(id1)
        assertNotNull(result)
    }
}