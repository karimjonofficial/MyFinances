package com.orka.myfinances.factories

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.SpyWarehouseApiService
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.SpyTemplateRepository
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
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
    private val productApiService = DummyProductApiService()
    private val warehouseApiService = SpyWarehouseApiService()
    private val templateRepository = SpyTemplateRepository()
    private val folderRepository = DummyFolderRepository()
    private val warehouseRepository = WarehouseRepository(warehouseApiService)
    private val productRepository = ProductRepository(productApiService)
    private val addTemplateScreenViewModel = AddTemplateScreenViewModel(templateRepository, coroutineContext)
    private val templatesScreenViewModel = TemplatesScreenViewModel(templateRepository, logger, coroutineContext)
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger, coroutineContext)
    private val addProductScreenViewModel = AddProductScreenViewModel(productRepository, warehouseRepository, logger, coroutineContext)
    private val provider = ViewModelProviderImpl(addTemplateScreenViewModel, addProductScreenViewModel, templatesScreenViewModel, homeScreenViewModel)

    @Test
    fun `Returns same add template view model`() {
        assertEquals(addTemplateScreenViewModel, provider.addTemplateViewModel())
    }

    @Test
    fun `Returns same templates view model`() {
        assertEquals(templatesScreenViewModel, provider.templatesViewModel())
    }

    @Test
    fun `Return same home viewmodel`() {
        assertEquals(homeScreenViewModel, provider.homeViewModel())
    }

    @Test
    fun `Returns the same add product viewmodel`() {
        assertEquals(addProductScreenViewModel, provider.addProductViewModel())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When templates viewmodel required, initializes it`() {
        provider.templatesViewModel()
        testScope.advanceUntilIdle()
        assertTrue(templateRepository.getCalled)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When add product viewmodel required, initializes it`() {
        provider.addProductViewModel()
        testScope.advanceUntilIdle()
        assertTrue(warehouseApiService.getCalled)
    }
}