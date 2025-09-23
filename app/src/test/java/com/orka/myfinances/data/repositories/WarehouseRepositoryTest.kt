package com.orka.myfinances.data.repositories

import com.orka.myfinances.core.MainDispatcherContext
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class WarehouseRepositoryTest : MainDispatcherContext() {
    private fun repository(apiService: WarehouseApiService): WarehouseRepository {
        return WarehouseRepository(apiService)
    }

    @Test
    fun `When api fails, return null`() = testScope.runTest {
        val repository = repository(EmptyWarehouseApiServiceStub())
        val result = repository.get()
        assertNull(result)
    }

    @Test
    fun `When api successes, return what api returns`() = testScope.runTest {
        val repository = repository(WarehouseApiServiceStub())
        val result = repository.get()
        assertNotNull(result)
    }
}