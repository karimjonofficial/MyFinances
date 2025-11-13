package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.core.assertTopIs
import com.orka.myfinances.core.testParameterizedBehavior
import com.orka.myfinances.core.testSingletonBehavior
import com.orka.myfinances.core.testTemporaryBehavior
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.testLib.template
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
    private val provider = SpyViewModelProvider()
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger)
    private val catalog1 = Catalog(Id(1), "cat1")
    private val catalog2 = Catalog(Id(2), "cat2")
    private val w1 = Warehouse(Id(1), "w1", template)
    private val w2 = Warehouse(Id(2), "w2", template)

    @Test
    fun `Back at root destination does nothing`() {
        navigationManager.back()
        assertTopIs(Destination.Home::class, size = 1, navigationManager)
    }

    @Test
    fun `Profile follows singleton behavior`() = testSingletonBehavior(
        navigate = { navigationManager.navigateToProfile() },
        destination = Destination.Profile::class, navigationManager
    )

    @Test
    fun `Settings follows singleton behavior`() = testSingletonBehavior(
        navigate = { navigationManager.navigateToSettings() },
        destination = Destination.Settings::class, navigationManager
    )

    @Test
    fun `Notifications follows singleton behavior`() = testSingletonBehavior(
        navigate = { navigationManager.navigateToNotifications() },
        destination = Destination.Notifications::class, navigationManager
    )


    @Test
    fun `Templates follows singleton behavior`() = testSingletonBehavior(
        navigate = { navigationManager.navigateToTemplates() },
        destination = Destination.Templates::class, navigationManager
    )

    @Test
    fun `AddProduct behaves as temporary child`() = testTemporaryBehavior(
        openParent = { navigationManager.navigateToTemplates() },
        navigateTemp = {
            navigationManager.navigateToAddProduct(
                Warehouse(Id(1), "wh", template)
            )
        },
        parent = Destination.Templates::class,
        temp = Destination.AddProduct::class, navigationManager
    )

    @Test
    fun `Catalog follows parameterized behavior`() = testParameterizedBehavior(
        first = catalog1,
        second = catalog2,
        navigate = { navigationManager.navigateToCatalog(it) },
        destination = Destination.Catalog::class, navigationManager
    )

    @Test
    fun `Warehouse follows parameterized behavior`() = testParameterizedBehavior(
        first = w1,
        second = w2,
        navigate = { navigationManager.navigateToWarehouse(it) },
        destination = Destination.Warehouse::class, navigationManager
    )

    @Test
    fun `AddTemplate follows temporary behavior from Home`() = testTemporaryBehavior(
        openParent = { navigationManager.navigateToHome() },
        navigateTemp = { navigationManager.navigateToAddTemplate() },
        parent = Destination.Home::class,
        temp = Destination.AddTemplate::class, navigationManager
    )

    @Test
    fun `Product follows singleton behavior`() = testSingletonBehavior(
        navigate = { navigationManager.navigateToProduct(product1) },
        destination = Destination.Product::class, navigationManager
    )

    @Test
    fun `Test Basket navigation`() = testSingletonBehavior(
        navigate = { navigationManager.navigateToBasket() },
        destination = Destination.Basket::class, navigationManager
    )

    @Test
    fun `Navigation state is Home when initialized`() {
        assertTrue { navigationManager.navigationState.value is Destination.Home }
    }

    @Test
    fun `When navigate to profile, state is profile`() {
        navigationManager.navigateToProfile()
        assertTrue { navigationManager.navigationState.value is Destination.Profile }
    }

    @Test
    fun `When navigate to settings, state is settings`() {
        navigationManager.navigateToSettings()
        assertTrue { navigationManager.navigationState.value is Destination.Settings }
    }

    @Nested
    inner class NavigateToBasketContext {
        @BeforeEach
        fun setup() {
            navigationManager.navigateToBasket()
        }

        @Test
        fun `When navigate to basket, state is basket`() {
            assertTrue { navigationManager.navigationState.value is Destination.Basket }
        }

        @Test
        fun `When navigate to basket and back, state is home`() {
            navigationManager.back()
            assertTrue { navigationManager.navigationState.value is Destination.Home }
        }

        @Test
        fun `When navigate to basket and home, state is home`() {
            navigationManager.navigateToHome()
            assertTrue { navigationManager.navigationState.value is Destination.Home }
        }
    }
}