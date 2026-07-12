package com.orka.myfinances.application.viewmodels.client.details

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.clientDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClientScreenViewModelTest : MainDispatcherContext() {
    private val id = Id(1)
    private val getById = mockk<GetById<ClientDto>>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(id) } returns clientDto1

        val viewModel = ClientScreenViewModel(id, getById, navigator, loading, failure, logger)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
