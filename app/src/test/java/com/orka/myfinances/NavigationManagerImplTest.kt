package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.ViewModelProviderStub
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.testLib.id
import com.orka.myfinances.testLib.name
import com.orka.myfinances.testLib.template
import com.orka.myfinances.testLib.warehouse
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.reflect.KClass

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger, coroutineContext)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
    private val provider = ViewModelProviderStub()
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger, coroutineContext)

    private fun assertTopIs(expected: KClass<out Destination>, size: Int? = null) {
        val last = navigationManager.backStack.value.last()
        assertTrue(expected.isInstance(last)) {
            "Expected top of stack to be ${expected.simpleName}, but was $last"
        }
        size?.let { assertEquals(it, navigationManager.backStack.value.size) }
    }

    private fun <T : Destination> assertTopEquals(expected: T, size: Int? = null) {
        val last = navigationManager.backStack.value.last()
        assertEquals(expected, last)
        size?.let { assertEquals(it, navigationManager.backStack.value.size) }
    }

    private inline fun <reified T : Destination> count(): Int = navigationManager.backStack.value.count { it is T }

    @Test
    fun nothing() {
        assertTopIs(Destination.Home::class, size = 1)
    }

    @Test
    fun `When only home left, back does not work`() {
        navigationManager.back()
        assertTopIs(Destination.Home::class, size = 1)
    }

    @Test
    fun `When only home left, navigate to home does not work`() {
        navigationManager.navigateToHome()
        assertTopIs(Destination.Home::class, size = 1)
    }

    @Nested
    inner class NavigateToProfileContext {
        @BeforeEach
        fun setup() = navigationManager.navigateToProfile()

        @Test
        fun `Should have Profile`() {
            assertTopIs(Destination.Profile::class, size = 2)
        }

        @Test
        fun `Back leaves only Home`() {
            navigationManager.back()
            assertTopIs(Destination.Home::class, size = 1)
        }

        @Test
        fun `Navigate to profile does not work`() {
            navigationManager.navigateToProfile()
            assertTopIs(Destination.Profile::class, size = 2)
        }

        @Test
        fun `When navigate to templates, and go back leaves Profile`() {
            navigationManager.navigateToTemplates()
            navigationManager.back()
            assertTopIs(Destination.Profile::class, size = 2)
        }
    }

    @Nested
    inner class NavigateToProductFolderContext {
        private val folder = Warehouse(id, name, template, emptyList(), emptyList())

        @BeforeEach
        fun setup() = navigationManager.navigateToProductFolder(folder)

        @Test
        fun `When navigate to folder, add Folder`() {
            assertTopEquals(Destination.Warehouse(folder), size = 2)
        }

        @Nested
        inner class NavigateToProductFolder2Context {
            private val folder2 = Warehouse(Id(2), name, template, emptyList(), emptyList())

            @BeforeEach
            fun setup() = navigationManager.navigateToProductFolder(folder2)

            @Test
            fun `Allow multiple folders in backstack`() {
                assertTopEquals(Destination.Warehouse(folder2), size = 3)
            }

            @Test
            fun `Back navigates to Folder`() {
                navigationManager.back()
                assertTopEquals(Destination.Warehouse(folder), size = 2)
            }
        }

        @Test
        fun `Does not allow similar folders in backstack`() {
            navigationManager.navigateToProductFolder(folder)
            assertTopEquals(Destination.Warehouse(folder), size = 2)
        }

        @Test
        fun `Navigate to home leaves only Home`() {
            navigationManager.navigateToHome()
            assertTopIs(Destination.Home::class, size = 1)
        }
    }

    @Nested
    inner class NavigateToCatalogContext {
        private val catalog = Catalog(id, name, emptyList())

        @BeforeEach
        fun setup() = navigationManager.navigateToCatalog(catalog)

        @Test
        fun `When navigate to catalog, add Catalog`() {
            assertTopEquals(Destination.Catalog(catalog), size = 2)
        }

        @Nested
        inner class NavigateToCatalog2Context {
            private val catalog2 = Catalog(Id(2), name, emptyList())

            @BeforeEach
            fun setup() = navigationManager.navigateToCatalog(catalog2)

            @Test
            fun `Allow multiple catalogs in backstack`() {
                assertTopEquals(Destination.Catalog(catalog2), size = 3)
            }

            @Test
            fun `Back navigates to Catalog`() {
                navigationManager.back()
                assertTopEquals(Destination.Catalog(catalog), size = 2)
            }
        }

        @Test
        fun `Does not allow similar catalogs in backstack`() {
            navigationManager.navigateToCatalog(catalog)
            assertTopEquals(Destination.Catalog(catalog), size = 2)
        }

        @Test
        fun `Navigate to home leaves only Home`() {
            navigationManager.navigateToHome()
            assertTopIs(Destination.Home::class, size = 1)
        }
    }

    @Nested
    inner class NavigateToNotifications {
        @BeforeEach
        fun setup() = navigationManager.navigateToNotifications()

        @Test
        fun `Should have Notifications`() {
            assertTopIs(Destination.Notifications::class)
        }

        @Test
        fun `Does not allow duplicate Notifications`() {
            navigationManager.navigateToNotifications()
            assertEquals(1, count<Destination.Notifications>())
        }
    }

    @Test
    fun `When navigate to add template, should have add template`() {
        navigationManager.navigateToAddTemplate()
        assertTopIs(Destination.AddTemplate::class, size = 2)
    }

    @Nested
    inner class NavigateToSettingsContext {
        @BeforeEach
        fun setup() = navigationManager.navigateToSettings()

        @Test
        fun `Should have Settings`() {
            assertTopIs(Destination.Settings::class, size = 2)
        }

        @Test
        fun `Back leaves only Home`() {
            navigationManager.back()
            assertTopIs(Destination.Home::class, size = 1)
        }

        @Test
        fun `Navigate to settings does not work`() {
            navigationManager.navigateToSettings()
            assertTopIs(Destination.Settings::class, size = 2)
        }
    }

    @Nested
    inner class NavigateToTemplatesContext {
        @BeforeEach
        fun setup() = navigationManager.navigateToTemplates()

        @Test
        fun `Should add Templates`() {
            assertTopIs(Destination.Templates::class, size = 2)
        }

        @Test
        fun `Has the same ViewModel`() {
            val state = navigationManager.backStack.value.last() as Destination.Templates
            assertEquals("templates", state.viewModel)
        }

        @Test
        fun `Does not allow duplicate templates`() {
            navigationManager.navigateToTemplates()
            assertTopIs(Destination.Templates::class, size = 2)
        }

        @Test
        fun `When navigate to add product and back, should have templates`() {
            navigationManager.navigateToAddProduct(warehouse)
            navigationManager.back()
            assertTopIs(Destination.Templates::class, size = 2)
        }
    }

    @Test
    fun `When navigate to add product, should have add template`() {
        navigationManager.navigateToAddProduct(warehouse)
        assertTopIs(Destination.AddProduct::class, size = 2)
    }
}