package com.orka.myfinances.application.viewmodels.product.details

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.testFixtures.resources.dtos.productTitleDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductTitleScreenViewModelTest : MainDispatcherContext() {
    private val productId = Id(1)
    private val getById = mockk<GetById<ProductTitleDto>>()
    private val insertReceive = mockk<Insert<AddReceiveRequest>>()
    private val productTitleEvents = MutableSharedFlow<ProductTitleEvent>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getById.getById(productId) } returns productTitleDto1

        val viewModel = ProductTitleScreenViewModel(
            productId, getById, insertReceive, productTitleEvents, navigator, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
