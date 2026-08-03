package com.orka.myfinances.application.viewmodels.template.details

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.testFixtures.resources.dtos.templateDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TemplateScreenViewModelTest : MainDispatcherContext() {
    private val id = Id(1)
    private val getById = mockk<GetById<TemplateDto>>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(id) } returns templateDto1

        val viewModel = TemplateScreenViewModel(
            id, getById, navigator, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
