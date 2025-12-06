package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.core.assertHome
import com.orka.myfinances.core.assertSize
import com.orka.myfinances.core.assertTopIs
import com.orka.myfinances.core.test
import com.orka.myfinances.data.repositories.basket.BasketRepository
import com.orka.myfinances.data.repositories.product.ProductRepository
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.api.product.DummyProductApiService
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.viewmodel.BasketContentViewModel
import com.orka.myfinances.ui.screens.home.viewmodel.FoldersContentViewModel
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val productApi = DummyProductApiService()
    private val productRepository = ProductRepository(productApi)
    private val basketRepository = BasketRepository(productRepository)
    private val basketViewModel = BasketContentViewModel(basketRepository, logger, testScope)
    private val foldersContentViewModel = FoldersContentViewModel(folderRepository, logger)
    private val initialBackStack = listOf(Destination.Home(foldersContentViewModel, basketViewModel))
    private val provider = SpyViewModelProvider()
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger)

    @Test
    fun `Any navigation adds destination on top`() = navigationManager.test {
        assertTopIs<Destination.Home>()
        assertSize(1)

        navigateToHome()
        assertTopIs<Destination.Home>()
        assertSize(2)
    }

    @Test
    fun `When backstack has only Home back does not work`() = navigationManager.test {
        back()
        assertHome()
    }

    @Test
    fun `When destination is not nav item, allow further`() = navigationManager.test {
        navigateToSettings()
        navigateToProduct(product1)
        back()
        assertTopIs<Destination.Settings>()
    }
}