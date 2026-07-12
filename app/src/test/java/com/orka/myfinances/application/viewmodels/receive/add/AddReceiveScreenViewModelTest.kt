package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.categoryDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AddReceiveScreenViewModelTest : MainDispatcherContext() {
    private val getFolder = mockk<GetById<FolderDto>>()
    private val insertReceive = mockk<Insert<AddReceiveRequest>>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val categoryId = Id(1)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getFolder.getById(categoryId) } returns categoryDto1

        val viewModel = AddReceiveScreenViewModel(
            categoryId, getFolder, insertReceive, navigator, loading, failure, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
