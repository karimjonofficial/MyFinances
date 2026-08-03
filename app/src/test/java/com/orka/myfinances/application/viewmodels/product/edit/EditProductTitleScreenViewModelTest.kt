package com.orka.myfinances.application.viewmodels.product.edit

import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.UpdateProductTitle
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.testFixtures.resources.dtos.categoryDto1
import com.orka.myfinances.testFixtures.resources.dtos.productTitleDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class EditProductTitleScreenViewModelTest : MainDispatcherContext() {
    private val getFolders = mockk<Get<FolderDto>>()
    private val productTitleRepository = mockk<GetById<ProductTitleDto>>()
    private val updateTitle = mockk<UpdateProductTitle>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val productId = Id(1)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getFolders.getAll() } returns listOf(categoryDto1)
        coEvery { productTitleRepository.getById(productId) } returns productTitleDto1

        val viewModel = EditProductTitleScreenViewModel(
            productId, getFolders, productTitleRepository, updateTitle, navigator, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
