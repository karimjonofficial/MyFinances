package com.orka.myfinances.application.viewmodels.template.add

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.template.requests.AddTemplateRequest
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.Test

class AddTemplateScreenViewModelTest : MainDispatcherContext() {
    private val insert = mockk<Insert<AddTemplateRequest>>()
    private val navigator = mockk<Navigator>(relaxed = true)

    @Test
    fun `addTemplate success`() = runTest {
        coEvery { insert.insert(any()) } returns true

        val viewModel = AddTemplateScreenViewModel(insert, navigator)
        
        viewModel.addTemplate("Name", emptyList())
        advanceUntilIdle()

        coVerify { navigator.back() }
    }
}
