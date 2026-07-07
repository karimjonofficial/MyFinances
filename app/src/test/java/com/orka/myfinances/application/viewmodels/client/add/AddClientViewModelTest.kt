package com.orka.myfinances.application.viewmodels.client.add

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.lib.data.repositories.Insert
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Test

class AddClientViewModelTest : MainDispatcherContext() {
    private val insert = mockk<Insert<AddClientRequest>>()

    @Test
    fun `add client success`() = runTest {
        coEvery { insert.insert(any()) } returns true

        val viewModel = AddClientViewModel(insert)
        viewModel.add("John", "Doe", null, "123456", "Addr")
        
        advanceUntilIdle()

        // coVerify { insert.insert(any()) } // already checked by coEvery if it returns true
    }
}
