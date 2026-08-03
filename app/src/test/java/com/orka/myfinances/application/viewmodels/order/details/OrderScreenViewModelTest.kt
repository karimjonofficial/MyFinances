package com.orka.myfinances.application.viewmodels.order.details

import com.orka.myfinances.data.dtos.order.OrderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.order.CompleteOrder
import com.orka.myfinances.data.repositories.order.SetEndDate
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.testFixtures.resources.dtos.orderDto1
import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class OrderScreenViewModelTest : MainDispatcherContext() {
    private val id = Id(1)
    private val getById = mockk<GetById<OrderDto>>()
    private val completeOrder = mockk<CompleteOrder>()
    private val setEndDate = mockk<SetEndDate>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(id) } returns orderDto1

        val viewModel = OrderScreenViewModel(
            id, getById, completeOrder, setEndDate, navigator, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
