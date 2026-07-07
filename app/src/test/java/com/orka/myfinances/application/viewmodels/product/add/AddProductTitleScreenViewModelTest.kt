package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.categoryDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddProductTitleScreenViewModelTest : MainDispatcherContext() {
    private val getFolders = mockk<Get<FolderDto>>()
    private val insertTitle = mockk<Insert<AddProductTitleRequest>>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val categoryId = Id(1)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getFolders.getAll(any()) } returns listOf(categoryDto1)

        val viewModel = AddProductTitleScreenViewModel(
            categoryId, getFolders, insertTitle, navigator, loading, failure, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
