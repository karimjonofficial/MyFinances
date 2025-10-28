package com.orka.myfinances.factories

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.WarehouseRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.SpyWarehouseApiService
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.SpyTemplateRepository
import com.orka.myfinances.fixtures.resources.models.folder.folder1
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

class ViewModelProviderImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val productApiService = DummyProductApiService()
    private val warehouseApiService = SpyWarehouseApiService()
    private val templateRepository = SpyTemplateRepository()
    private val folderRepository = DummyFolderRepository()
    private val warehouseRepository = WarehouseRepository(warehouseApiService)
    private val productRepository = ProductRepository(productApiService)
    private val addTemplateScreenViewModel = AddTemplateScreenViewModel(
        repository = templateRepository,
        coroutineScope = testScope
    )
    private val templatesScreenViewModel = TemplatesScreenViewModel(
            repository = templateRepository,
            logger = logger,
        coroutineScope = testScope
        )
    private val homeScreenViewModel = HomeScreenViewModel(
            repository = folderRepository,
            logger = logger,
        coroutineScope = testScope
        )
    private val addProductScreenViewModel = AddProductScreenViewModel(
        productRepository = productRepository,
        warehouseRepository = warehouseRepository,
        logger = logger,
        coroutineScope = testScope
    )
    private val warehouseScreenViewModelProvider = object : WarehouseScreenViewModelProvider {
        override fun warehouseViewModel(warehouse: Warehouse): WarehouseScreenViewModel {
            return WarehouseScreenViewModel(
                warehouse = warehouse,
                productRepository = productRepository,
                warehouseRepository = warehouseRepository,
                logger = logger,
                coroutineScope = testScope
            )
        }
    }
    private val provider = ViewModelProviderImpl(
        addTemplateScreenViewModel = addTemplateScreenViewModel,
        addProductScreenViewModel = addProductScreenViewModel,
        templatesScreenViewModel = templatesScreenViewModel,
        homeScreenViewModel = homeScreenViewModel,
        warehouseScreenViewModelProvider = warehouseScreenViewModelProvider
    )

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

    @Test
    fun `Returns warehouse viewmodel`() {
        assertNotNull(provider.warehouseViewModel(folder1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Initializes warehouse viewmodel`() {
        provider.warehouseViewModel(folder1)
        testScope.advanceUntilIdle()
        assertTrue(warehouseApiService.getCalled)
    }
}