package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.core.assertHome
import com.orka.myfinances.core.assertSize
import com.orka.myfinances.core.assertTopIs
import com.orka.myfinances.core.test
import com.orka.myfinances.core.testNavigationItems
import com.orka.myfinances.core.testParameterizedBehavior
import com.orka.myfinances.core.testSingleton
import com.orka.myfinances.core.testTemporaryBehavior
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.testLib.catalog1
import com.orka.myfinances.testLib.catalog2
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.testLib.warehouse1
import com.orka.myfinances.testLib.warehouse2
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
    private val provider = SpyViewModelProvider()
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger)

    @Test
    fun `When navigate to home no any change`() = navigationManager.test {
        navigateToHome()
        assertHome()
    }

    @Nested
    inner class BackTestsContext {
        @Test
        fun `Back at root destination does nothing`() = navigationManager.test {
            back()
            assertHome()
        }

        @Test
        fun `When navigate to nav item and back, destination is home`() = navigationManager.test {
            navigateToSettings()
            back()
            assertHome()
        }

        @Test
        fun `When the previous is not home back goes to previous`() = navigationManager.test {
            navigateToSettings()
            navigateToTemplates()
            back()
            assertSize(2)
            assertTopIs<Destination.Settings>("Expected Settings")
        }
    }

    @Test
    fun `Profile follows singleton behavior`() =
        navigationManager.testNavigationItems<Destination.Profile> {
            navigateToProfile()
        }

    @Test
    fun `Settings follows singleton behavior`() =
        navigationManager.testNavigationItems<Destination.Settings> {
            navigateToSettings()
        }

    @Test
    fun `Notifications singleton item behavior`() =
        navigationManager.testSingleton<Destination.Notifications> {
            navigateToNotifications()
        }


    @Test
    fun `Templates follows singleton behavior`() =
        navigationManager.testSingleton<Destination.Templates> {
            navigateToTemplates()
        }

    @Test
    fun `AddProduct behaves as temporary child`() =
        navigationManager.testTemporaryBehavior<Destination.AddProduct>(
            navigateTemp = { navigateToAddProduct(warehouse1) }
        )

    @Test
    fun `Catalog follows parameterized behavior`() =
        navigationManager.testParameterizedBehavior<Destination.Catalog, Catalog>(
            first = catalog1,
            second = catalog2,
            navigate = { navigateToCatalog(it) }
        )

    @Test
    fun `Warehouse follows parameterized behavior`() =
        navigationManager.testParameterizedBehavior<Destination.Warehouse, Warehouse>(
            first = warehouse1,
            second = warehouse2,
            navigate = { navigateToWarehouse(it) }
        )

    @Test
    fun `AddTemplate follows temporary behavior from Home`() =
        navigationManager.testTemporaryBehavior<Destination.AddTemplate> {
            navigateToAddTemplate()
        }

    @Test
    fun `Product follows singleton behavior`() =
        navigationManager.testSingleton<Destination.Product> {
            navigateToProduct(product1)
        }

    @Test
    fun `Basket follows navigation item behavior`() =
        navigationManager.testNavigationItems<Destination.Basket> {
            navigateToBasket()
        }

    @Test
    fun `Navigation state is Home when initialized`() = navigationManager.test {
        assertTopIs<Destination.Home>()
    }

    @Test
    fun `When navigate to profile, state is profile`() = navigationManager.test {
        navigateToProfile()
        assertTopIs<Destination.Profile>()
    }

    @Test
    fun `When navigate to settings, state is settings`() = navigationManager.test {
        navigateToSettings()
        assertTopIs<Destination.Settings>()
    }
}