package com.orka.myfinances.application.viewmodels.debt.list

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.debt.DebtEvent
import com.orka.myfinances.lib.data.repositories.GetChunk
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.format.FormatLocalDate
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.format.FormatTime
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.debtDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DebtsScreenViewModelTest : MainDispatcherContext() {
    private val getChunk = mockk<GetChunk<DebtDto>>()
    private val insert = mockk<Insert<AddDebtRequest>>()
    private val events = MutableSharedFlow<DebtEvent>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatLocalDate = mockk<FormatLocalDate>()
    private val formatTime = mockk<FormatTime>()
    private val logger = mockk<Logger>(relaxed = true)
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(debtDto1)
        )
        coEvery { getChunk.getChunk(any(), any(), any()) } returns chunk
        every { formatLocalDate.formatLocalDate(any()) } returns "2024-01-01"
        every { formatPrice.formatPrice(any()) } returns "100,000"
        every { formatTime.formatTime(any()) } returns "12:00"

        val viewModel = DebtsScreenViewModel(
            getChunk, insert, events, formatPrice, formatLocalDate, formatTime, loading, failure, logger, navigator
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
