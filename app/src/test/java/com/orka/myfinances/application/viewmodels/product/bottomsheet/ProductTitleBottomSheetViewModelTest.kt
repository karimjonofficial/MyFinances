package com.orka.myfinances.application.viewmodels.product.bottomsheet

import com.orka.myfinances.application.viewmodels.product.sheet.ProductTitleBottomSheetViewModel
import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.GetProductTitlesByCategory
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.data.repositories.Chunk
import com.orka.myfinances.testFixtures.resources.dtos.productTitleDto1
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ProductTitleBottomSheetViewModelTest : MainDispatcherContext() {
    private val getByCategory = mockk<GetProductTitlesByCategory>()
    private val flow = MutableSharedFlow<ProductTitleEvent>()
    private val categoryId = Id(1)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        val chunk = Chunk(
            count = 1,
            pageIndex = 1,
            nextPageIndex = null,
            previousPageIndex = null,
            results = listOf(productTitleDto1)
        )
        coEvery {
            getByCategory.getByCategory(
                size = any(),
                pageIndex = any(),
                categoryId = any(),
                search = any()
            )
        } returns chunk

        val viewModel = ProductTitleBottomSheetViewModel(
            categoryId = categoryId,
            getByCategory = getByCategory,
            flow = flow,
            logger = logger
        )
        // initialize() is called in init
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
