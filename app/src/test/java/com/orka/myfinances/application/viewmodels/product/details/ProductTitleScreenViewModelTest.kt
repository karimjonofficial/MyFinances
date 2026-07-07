package com.orka.myfinances.application.viewmodels.product.details

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.productTitleDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductTitleScreenViewModelTest : MainDispatcherContext() {
    private val productId = Id(1)
    private val getById = mockk<GetById<ProductTitleDto>>()
    private val insertReceive = mockk<Insert<AddReceiveRequest>>()
    private val productTitleEvents = MutableSharedFlow<ProductTitleEvent>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val formatDate = mockk<FormatDate>()
    private val formatPrice = mockk<FormatPrice>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(productId) } returns productTitleDto1
        every { formatDecimal.formatDecimal(any()) } returns "10.0"
        every { formatDate.formatDate(any()) } returns "01.01.2024"
        every { formatPrice.formatPrice(any()) } returns "100.0"

        val viewModel = ProductTitleScreenViewModel(
            productId, getById, insertReceive, productTitleEvents, formatDecimal, formatDate, formatPrice, navigator, loading, failure, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
