package com.orka.myfinances.application.viewmodels.template.bottomsheet

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.templateDto1
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TemplateBottomSheetViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<TemplateDto>>()
    private val events = MutableSharedFlow<TemplateEvent>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(templateDto1)
        )
        coEvery { getChunk.getChunk(any(), any(), any()) } returns chunk

        val viewModel = TemplateBottomSheetViewModel(
            getChunk, events, loading, failure, logger
        )
        viewModel.initialize()
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
