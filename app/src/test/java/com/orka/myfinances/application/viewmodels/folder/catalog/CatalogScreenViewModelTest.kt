package com.orka.myfinances.application.viewmodels.folder.catalog

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.dtos.folder.FolderDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.folder.AddFolderRequest
import com.orka.myfinances.data.repositories.folder.FolderEvent
import com.orka.myfinances.data.repositories.folder.GetByParent
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.testFixtures.resources.dtos.catalogDto1
import com.orka.myfinances.ui.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CatalogScreenViewModelTest : MainDispatcherContext() {
    private val catalogId = Id(2)
    private val getByParent = mockk<GetByParent>()
    private val getById = mockk<GetById<FolderDto>>()
    private val add = mockk<Add<Unit, AddFolderRequest>>()
    private val loading = UiText.Str("Loading")
    private val failure = UiText.Str("Failure")
    private val events = MutableSharedFlow<FolderEvent>()
    private val navigator = mockk<Navigator>(relaxed = true)
    private val logger = mockk<Logger>(relaxed = true)

    @Test
    fun `initialize success`() = runTest {
        coEvery { getByParent.getByParent(catalogId) } returns emptyList()
        coEvery { getById.getById(catalogId) } returns catalogDto1

        val viewModel = CatalogScreenViewModel(
            catalogId, getByParent, getById, add, loading, failure, events, navigator, logger
        )
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is State.Success)
    }
}
