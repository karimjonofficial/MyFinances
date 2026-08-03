package com.orka.myfinances.application.viewmodels.client.add

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.logger.Logger
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test

class AddClientViewModelTest : MainDispatcherContext() {
    private val insert = mockk<Insert<AddClientRequest>>()
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `add client success`() = runTest {
        coEvery { insert.insert(any()) } returns true

        val viewModel = AddClientViewModel(insert, logger)
        viewModel.add("John", "Doe", null, "123456", "Address")
        
        advanceUntilIdle()
    }
}
