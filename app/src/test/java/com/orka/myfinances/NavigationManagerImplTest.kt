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
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
    private val provider = SpyViewModelProvider()
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger)

    @Nested
    inner class BackBehaviorContext {
        @Test
        fun `Back at root destination does nothing`() {
            navigationManager.back()
            assertTopIs(Destination.Home::class, size = 1, navigationManager)
        }
    }

    @Nested
    inner class NavigateToProfileContext {
        @Test
        fun `Profile follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToProfile() },
            destination = Destination.Profile::class, navigationManager
        )
    }

    @Nested
    inner class NavigateToSettingsContext {
        @Test
        fun `Settings follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToSettings() },
            destination = Destination.Settings::class, navigationManager
        )
    }

    @Nested
    inner class NavigateToNotificationsContext {
        @Test
        fun `Notifications follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToNotifications() },
            destination = Destination.Notifications::class, navigationManager
        )
    }

    @Nested
    inner class NavigateToTemplatesContext {
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
    }

    @Nested
    inner class NavigateToCatalogContext {
        private val catalog1 = Catalog(Id(1), "cat1")
        private val catalog2 = Catalog(Id(2), "cat2")

        @Test
        fun `Catalog follows parameterized behavior`() = testParameterizedBehavior(
            first = catalog1,
            second = catalog2,
            navigate = { navigationManager.navigateToCatalog(it) },
            destination = Destination.Catalog::class, navigationManager
        )
    }

    @Nested
    inner class NavigateToWarehouseContext {
        private val w1 = Warehouse(Id(1), "w1", template)
        private val w2 = Warehouse(Id(2), "w2", template)

        @Test
        fun `Warehouse follows parameterized behavior`() = testParameterizedBehavior(
            first = w1,
            second = w2,
            navigate = { navigationManager.navigateToWarehouse(it) },
            destination = Destination.Warehouse::class, navigationManager
        )
    }

    @Nested
    inner class NavigateToAddTemplateContext {
        @Test
        fun `AddTemplate follows temporary behavior from Home`() = testTemporaryBehavior(
            openParent = { navigationManager.navigateToHome() },
            navigateTemp = { navigationManager.navigateToAddTemplate() },
            parent = Destination.Home::class,
            temp = Destination.AddTemplate::class, navigationManager
        )
    }

    @Nested
    inner class NavigateToProductContext {
        @Test
        fun `Product follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToProduct(product1) },
            destination = Destination.Product::class, navigationManager
        )
    }
}