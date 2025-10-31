package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.testLib.product
import com.orka.myfinances.testLib.template
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class NavigationManagerBehaviorTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
    private val provider = SpyViewModelProvider()
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger)

    private fun assertTopIs(expected: KClass<out Destination>, size: Int? = null) {
        val last = navigationManager.backStack.value.last()
        assertTrue(expected.isInstance(last)) { "Expected ${expected.simpleName}, but was $last" }
        size?.let { assertEquals(it, navigationManager.backStack.value.size) }
    }

    private fun testSingletonBehavior(
        navigate: () -> Unit,
        destination: KClass<out Destination>
    ) {
        navigate()
        assertTopIs(destination, size = 2)

        // Duplicates are ignored
        navigate()
        assertTopIs(destination, size = 2)

        // Back → returns Home
        navigationManager.back()
        assertTopIs(Destination.Home::class, size = 1)
    }

    private fun <T> testParameterizedBehavior(
        first: T,
        second: T,
        navigate: (T) -> Unit,
        destination: KClass<out Destination>
    ) {
        navigate(first)
        assertTopIs(destination, size = 2)

        // Duplicate → ignored
        navigate(first)
        assertTopIs(destination, size = 2)

        // Unique → added
        navigate(second)
        assertTopIs(destination, size = 3)

        // Back → returns to previous
        navigationManager.back()
        assertTopIs(destination, size = 2)
    }

    private fun testTemporaryBehavior(
        openParent: () -> Unit,
        navigateTemp: () -> Unit,
        parent: KClass<out Destination>,
        temp: KClass<out Destination>
    ) {
        val beforeParent = navigationManager.backStack.value.size
        openParent()
        val afterParent = navigationManager.backStack.value.size

        // Expected size after navigating to parent:
        // - If navigating to parent added a new screen → +1
        // - If already on parent (like Home) → same
        val expectedParentSize = if (afterParent > beforeParent) afterParent else beforeParent
        assertTopIs(parent, size = expectedParentSize)

        // Now navigate to the temporary screen
        navigateTemp()
        assertTopIs(temp, size = expectedParentSize + 1)

        // Go back → should return to parent
        navigationManager.back()
        assertTopIs(parent, size = expectedParentSize)
    }

    @Nested
    inner class BackBehaviorContext {
        @Test
        fun `Back at root destination does nothing`() {
            navigationManager.back()
            assertTopIs(Destination.Home::class, size = 1)
        }
    }

    @Nested
    inner class NavigateToProfileContext {
        @Test
        fun `Profile follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToProfile() },
            destination = Destination.Profile::class
        )
    }

    @Nested
    inner class NavigateToSettingsContext {
        @Test
        fun `Settings follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToSettings() },
            destination = Destination.Settings::class
        )
    }

    @Nested
    inner class NavigateToNotificationsContext {
        @Test
        fun `Notifications follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToNotifications() },
            destination = Destination.Notifications::class
        )
    }

    @Nested
    inner class NavigateToTemplatesContext {
        @Test
        fun `Templates follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToTemplates() },
            destination = Destination.Templates::class
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
            temp = Destination.AddProduct::class
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
            destination = Destination.Catalog::class
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
            destination = Destination.Warehouse::class
        )
    }

    @Nested
    inner class NavigateToAddTemplateContext {
        @Test
        fun `AddTemplate follows temporary behavior from Home`() = testTemporaryBehavior(
            openParent = { navigationManager.navigateToHome() },
            navigateTemp = { navigationManager.navigateToAddTemplate() },
            parent = Destination.Home::class,
            temp = Destination.AddTemplate::class
        )
    }

    @Nested
    inner class NavigateToProductContext {
        @Test
        fun `Product follows singleton behavior`() = testSingletonBehavior(
            navigate = { navigationManager.navigateToProduct(product) },
            destination = Destination.Product::class
        )
    }
}