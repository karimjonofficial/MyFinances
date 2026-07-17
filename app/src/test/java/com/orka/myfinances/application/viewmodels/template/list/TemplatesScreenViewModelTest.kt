package com.orka.myfinances.application.viewmodels.template.list

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.template.TemplateDto
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.templateDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class TemplatesScreenViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<TemplateDto>>()
    private val events = MutableSharedFlow<TemplateEvent>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val formatDecimal = mockk<FormatDecimal>()
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
            results = listOf(templateDto1)
        )
        coEvery { getChunk.getChunk(any(), any(), any()) } returns chunk
        every { formatDecimal.formatDecimal(any()) } returns "1.0"

        val viewModel = TemplatesScreenViewModel(
            getChunk, events, navigator, formatDecimal, loading, failure, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
