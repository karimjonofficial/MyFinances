package com.orka.myfinances.factories

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.SpyTemplateRepository
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ViewModelProviderImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val templateRepository = SpyTemplateRepository()
    private val folderRepository = DummyFolderRepository()
    private val addTemplateScreenViewModel = AddTemplateScreenViewModel(templateRepository, coroutineContext)
    private val templatesScreenViewModel = TemplatesScreenViewModel(templateRepository, logger, coroutineContext)
    private val homeViewModel = HomeScreenViewModel(folderRepository, logger, coroutineContext)
    private val provider = ViewModelProviderImpl(addTemplateScreenViewModel, templatesScreenViewModel, homeViewModel)

    @Test
    fun `Returns same add template view model`() {
        assertEquals(addTemplateScreenViewModel, provider.addTemplateViewModel())
    }

    @Test
    fun `Returns same templates view model`() {
        assertEquals(templatesScreenViewModel, provider.templatesViewModel())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When templates viewmodel required, initializes it`() {
        provider.templatesViewModel()
        testScope.advanceUntilIdle()
        assertTrue(templateRepository.getCalled)
    }

    @Test
    fun `Return same home viewmodel`() {
        assertEquals(homeViewModel, provider.homeViewModel())
    }
}