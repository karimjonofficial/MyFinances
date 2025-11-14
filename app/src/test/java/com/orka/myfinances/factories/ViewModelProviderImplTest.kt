package com.orka.myfinances.factories

import app.cash.turbine.test
import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.data.repositories.StockRepository
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.factories.viewmodel.CatalogScreenViewModelProvider
import com.orka.myfinances.factories.viewmodel.WarehouseScreenViewModelProvider
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.SpyProductApiService
import com.orka.myfinances.fixtures.data.api.warehouse.SpyStockApiService
import com.orka.myfinances.fixtures.data.repositories.folder.SpyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.SpyTemplateRepository
import com.orka.myfinances.fixtures.resources.models.folder.folder1
import com.orka.myfinances.testLib.catalog1
import com.orka.myfinances.ui.screens.add.product.viewmodel.AddProductScreenViewModel
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.basket.BasketScreenState
import com.orka.myfinances.ui.screens.basket.BasketScreenViewModel
import com.orka.myfinances.ui.screens.catalog.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import com.orka.myfinances.ui.screens.warehouse.viewmodel.WarehouseScreenViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull

class ViewModelProviderImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val productApiService = SpyProductApiService()
    private val warehouseApiService = SpyStockApiService()
    private val templateRepository = SpyTemplateRepository()
    private val folderRepository = SpyFolderRepository()
    private val stockRepository = StockRepository(warehouseApiService)
    private val productRepository = ProductRepository(productApiService)
    private val basketRepository = BasketRepository(productRepository)
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
        stockRepository = stockRepository,
        logger = logger,
        coroutineScope = testScope
    )
    private val catalogScreenViewModel = CatalogScreenViewModel(
        catalog = catalog1,
        repository = folderRepository,
        logger = logger,
        coroutineScope = testScope
    )
    private val warehouseScreenViewModelProvider = object : WarehouseScreenViewModelProvider {
        override fun warehouseViewModel(warehouse: Warehouse): WarehouseScreenViewModel {
            return WarehouseScreenViewModel(
                warehouse = warehouse,
                productRepository = productRepository,
                stockRepository = stockRepository,
                add = {},
                logger = logger,
                coroutineScope = testScope
            )
        }
    }
    private val catalogScreenViewModelProvider = object : CatalogScreenViewModelProvider {
        override fun catalogViewModel(catalog: Catalog): Any {
            return catalogScreenViewModel
        }
    }
    private val basketScreenViewModel = BasketScreenViewModel(
        repository = basketRepository,
        logger = logger,
        coroutineScope = testScope
    )
    private val provider = ViewModelProviderImpl(
        addTemplateScreenViewModel = addTemplateScreenViewModel,
        addProductScreenViewModel = addProductScreenViewModel,
        templatesScreenViewModel = templatesScreenViewModel,
        homeScreenViewModel = homeScreenViewModel,
        warehouseScreenViewModelProvider = warehouseScreenViewModelProvider,
        catalogScreenViewModelProvider = catalogScreenViewModelProvider,
        basketScreenViewModel = basketScreenViewModel
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

    @Test
    fun `Returns what catalog provider returns`() {
        assertEquals(catalogScreenViewModel, provider.catalogViewModel(catalog1))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Initializes catalog view model`() {
        provider.catalogViewModel(catalog1)
        testScope.advanceUntilIdle()
        assertTrue(folderRepository.id == catalog1.id)
    }

    @Test
    fun `Return same basket viewmodel`() {
        assertEquals(basketScreenViewModel, provider.basketViewModel())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When basket viewmodel required, initializes it`() = testScope.runTest {
        provider.basketViewModel()
        testScope.advanceUntilIdle()
        basketScreenViewModel.uiState.test {
            val state = awaitItem()
            assertTrue { state is BasketScreenState.Success }
        }
    }
}