package com.orka.myfinances.application.viewmodels.sale.details

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.format.FormatTime
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.testFixtures.resources.dtos.saleDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SaleScreenViewModelTest : MainDispatcherContext() {
    private val id = Id(1)
    private val printer = mockk<Printer>(relaxed = true)
    private val getById = mockk<GetById<SaleDto>>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatDate = mockk<FormatDate>()
    private val formatTime = mockk<FormatTime>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(id) } returns saleDto1
        every { formatPrice.formatPrice(any()) } returns "100.0"
        every { formatDate.formatDate(any()) } returns "01.01.2024"
        every { formatTime.formatTime(any()) } returns "12:00"
        every { formatDecimal.formatDecimal(any()) } returns "10.0"

        val viewModel = SaleScreenViewModel(
            id, printer, getById, formatPrice, formatDate, formatTime, formatDecimal, navigator, loading, failure, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
