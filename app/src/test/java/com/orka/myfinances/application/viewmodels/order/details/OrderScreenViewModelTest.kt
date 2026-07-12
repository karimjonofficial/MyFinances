package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.CompleteOrder
import com.orka.myfinances.data.repositories.order.SetEndDate
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.format.FormatDateTime
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.orderDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrderScreenViewModelTest : MainDispatcherContext() {
    private val id = Id(1)
    private val getById = mockk<GetById<OrderDto>>()
    private val completeOrder = mockk<CompleteOrder>()
    private val setEndDate = mockk<SetEndDate>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatDateTime = mockk<FormatDateTime>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(id) } returns orderDto1
        every { formatPrice.formatPrice(any()) } returns "100.0"
        every { formatDateTime.formatDateTime(any()) } returns "01.01.2024 12:00"
        every { formatDecimal.formatDecimal(any()) } returns "10.0"

        val viewModel = OrderScreenViewModel(
            id, getById, completeOrder, setEndDate, formatPrice, formatDateTime, formatDecimal, navigator, loading, failure, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
