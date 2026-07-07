package com.orka.myfinances.application.viewmodels.folder.home

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetTop
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.folderDtos
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FoldersContentViewModelTest : MainDispatcherContext() {
    private val getTop = mockk<GetTop>()
    private val add = mockk<Add<FolderDto, AddFolderRequest>>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val events = MutableSharedFlow<FolderEvent>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getTop.getTop(any()) } returns folderDtos

        val viewModel = FoldersContentViewModel(
            getTop, add, navigator, events, loading, failure, logger
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
