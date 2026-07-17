package com.orka.myfinances.application.viewmodels.debt.details

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.debt.DebtDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.debt.SetNotified
import com.orka.myfinances.data.repositories.debt.SetPaid
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.debtDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class DebtScreenViewModelTest : MainDispatcherContext() {
    private val id = Id(1)
    private val getById = mockk<GetById<DebtDto>>()
    private val setPaid = mockk<SetPaid>()
    private val setNotified = mockk<SetNotified>()
    private val formatPrice = mockk<FormatPrice>()
    private val formatDate = mockk<FormatDate>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(id) } returns debtDto1
        every { formatPrice.formatPrice(any()) } returns "100.0"
        every { formatDate.formatDate(any()) } returns "01.01.2024"

        val viewModel = DebtScreenViewModel(
            id, getById, setPaid, setNotified, formatPrice, formatDate, navigator, loading, failure, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
