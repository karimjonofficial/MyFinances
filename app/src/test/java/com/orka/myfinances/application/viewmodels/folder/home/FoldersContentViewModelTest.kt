package com.orka.myfinances.application.viewmodels.folder.home

import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesEvent
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetTop
import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesRepository
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.testFixtures.resources.dtos.folderDtos
import com.orka.myfinances.testLib.MainDispatcherContext
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class FoldersContentViewModelTest : MainDispatcherContext() {
    private val getTop = mockk<GetTop>()
    private val addFolder = mockk<Add<Unit, AddFolderRequest>>()
    private val addTitle = mockk<Add<Id, AddProductTitleRequest>>()
    private val addReceive = mockk<Insert<AddReceiveRequest>>()
    private val pinnedCategoriesRepository = mockk<PinnedCategoriesRepository>()
    private val getDefaultCategory = mockk<GetDefaultCategory>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val folderEvents = MutableSharedFlow<FolderEvent>()
    private val pinnedCategoriesFlow = MutableSharedFlow<PinnedCategoriesEvent>()
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getTop.getTop() } returns folderDtos
        coEvery { pinnedCategoriesRepository.getAll() } returns emptyList()
        coEvery { getDefaultCategory.getDefaultCategoryId() } returns null

        val viewModel = FoldersContentViewModel(
            getTop = getTop,
            addFolder = addFolder,
            addTitle = addTitle,
            addReceive = addReceive,
            pinnedCategoriesRepository = pinnedCategoriesRepository,
            getDefaultCategory = getDefaultCategory,
            navigator = navigator,
            folderFlow = folderEvents,
            pinnedCategoriesFlow = pinnedCategoriesFlow,
            logger = logger,
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
