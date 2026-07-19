package com.orka.myfinances.application.viewmodels.folder.home

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.defaults.DefaultsEvent
import com.orka.myfinances.data.repositories.defaults.GetDefaultCategory
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetTop
import com.orka.myfinances.data.repositories.preferences.categories.PinnedCategoriesRepository
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
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
    private val add = mockk<Add<Unit, AddFolderRequest>>()
    private val pinnedCategoriesRepository = mockk<PinnedCategoriesRepository>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val folderEvents = MutableSharedFlow<FolderEvent>()
    private val defaultsEvents = MutableSharedFlow<DefaultsEvent>()
    private val addTitle = mockk<Add<Id, AddProductTitleRequest>>()
    private val addReceive = mockk<Insert<AddReceiveRequest>>()
    private val getDefaultCategory = mockk<GetDefaultCategory>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getTop.getTop(any()) } returns folderDtos
        val viewModel = FoldersContentViewModel(
            getTop = getTop,
            addFolder = add,
            pinnedCategoriesRepository = pinnedCategoriesRepository,
            navigator = navigator,
            folderFlow = folderEvents,
            defaultsFlow = defaultsEvents,
            loading = loading,
            failure = failure,
            logger = logger,
            addTitle = addTitle,
            addReceive = addReceive,
            getDefaultCategory = getDefaultCategory,
        )
        
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
