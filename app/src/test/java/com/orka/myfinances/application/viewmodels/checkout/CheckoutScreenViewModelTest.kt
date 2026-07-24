package com.orka.myfinances.application.viewmodels.checkout

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.sale.SaleDto
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.data.repositories.order.AddOrderRequest
import com.orka.myfinances.data.repositories.sale.AddSaleRequest
import com.orka.myfinances.data.repositories.stock.GetStockItemByProduct
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.State
import com.orka.myfinances.printer.Printer
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CheckoutScreenViewModelTest : MainDispatcherContext() {
    private val addSale = mockk<Add<SaleDto, AddSaleRequest>>()
    private val insertOrder = mockk<Insert<AddOrderRequest>>()
    private val insertDebt = mockk<Insert<AddDebtRequest>>()
    private val stockRepository = mockk<GetStockItemByProduct>()
    private val basketRepository = mockk<BasketRepository>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val printer = mockk<Printer>(relaxed = true)
    private val formatPrice = mockk<FormatPrice>()
    private val formatDecimal = mockk<FormatDecimal>()
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val viewModel = CheckoutScreenViewModel(
        addSale = addSale,
        insertOrder = insertOrder,
        insertDebt = insertDebt,
        stockRepository = stockRepository,
        basketRepository = basketRepository,
        navigator = navigator,
        printer = printer,
        formatDecimal = formatDecimal,
        formatPrice = formatPrice,
        loading = loading,
        failure = failure,
        logger = logger
    )

    @Test
    fun `initialize success`() = runTest {
        coEvery { basketRepository.get() } returns emptyList()
        every { formatPrice.formatPrice(any()) } returns "0.0"

        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
