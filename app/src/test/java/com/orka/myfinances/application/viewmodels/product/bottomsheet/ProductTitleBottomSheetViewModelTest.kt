package com.orka.myfinances.application.viewmodels.product.bottomsheet

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.GetProductTitlesByCategory
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.Chunk
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
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
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
        val viewModel = ProductTitleBottomSheetViewModel(
            categoryId = categoryId,
            getByCategory = getByCategory,
            flow = flow,
            loading = loading,
            failure = failure,
            logger = logger
        )
        coEvery {
            getByCategory.getByCategory(
                size = any(),
                pageIndex = any(),
                categoryId = any(),
                search = any()
            )
        } returns chunk

        viewModel.initialize()
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
