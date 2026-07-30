package com.orka.myfinances.application.viewmodels.receive.list

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.receive.ReceiveDto
import com.orka.myfinances.data.repositories.receive.ReceiveEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatLocalDate
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.format.FormatTime
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.receiveDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ReceiveContentViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<ReceiveDto>>()
    private val events = MutableSharedFlow<ReceiveEvent>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatLocalDate = mockk<FormatLocalDate>()
    private val formatTime = mockk<FormatTime>()
    private val formatDecimal = mockk<FormatDecimal>()
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
            results = listOf(receiveDto1)
        )
        coEvery { getChunk.getChunk(any(), any(), any()) } returns chunk
        every { formatLocalDate.formatLocalDate(any()) } returns "2024-01-01"
        every { formatPrice.formatPrice(any()) } returns "5,000"
        every { formatTime.formatTime(any()) } returns "12:00"
        every { formatDecimal.formatDecimal(any()) } returns "5.0"

        val viewModel = ReceiveContentViewModel(
            getChunk, events, loading, failure, formatPrice, formatLocalDate, formatTime, formatDecimal, navigator, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
