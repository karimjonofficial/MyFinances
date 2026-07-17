package com.orka.myfinances.application.viewmodels.client.list

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.client.ClientDto
import com.orka.myfinances.data.repositories.client.AddClientRequest
import com.orka.myfinances.data.repositories.client.ClientEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.clientDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClientsScreenViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<ClientDto>>()
    private val insert = mockk<Insert<AddClientRequest>>()
    private val events = MutableSharedFlow<ClientEvent>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(clientDto1)
        )
        coEvery { getChunk.getChunk(any(), any(), any()) } returns chunk

        val viewModel = ClientsScreenViewModel(
            getChunk, insert, events, loading, failure, navigator, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
