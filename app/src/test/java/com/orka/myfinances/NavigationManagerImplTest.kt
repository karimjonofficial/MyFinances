package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.ProductFolder
import com.orka.myfinances.factories.ViewModelProvider
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.testLib.id
import com.orka.myfinances.testLib.name
import com.orka.myfinances.testLib.template
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.template.TemplateScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    val templateRepository = DummyTemplateRepository()
    val templateScreenViewModel = TemplateScreenViewModel(templateRepository)
    private val provider = ViewModelProvider(templateScreenViewModel)
    private val logger = DummyLogger()
    private val repository = DummyFolderRepository()
    private val viewModel = HomeScreenViewModel(repository, logger, coroutineContext)
    private val initialBackStack = listOf(Destination.Home(viewModel))
    private val manager = NavigationManagerImpl(initialBackStack, provider, logger, coroutineContext)

    @Test
    fun nothing() {
        assertTrue { manager.backStack.value.first() is Destination.Home }
    }

    @Test
    fun `When only home left, back does not work`() {
        manager.back()
        assertTrue { manager.backStack.value.first() is Destination.Home }
        assertTrue { manager.backStack.value.size == 1 }
    }

    @Test
    fun `When only home left, navigate to home does not work`() {
        manager.navigateToHome()
        assertTrue { manager.backStack.value.first() is Destination.Home }
        assertTrue { manager.backStack.value.size == 1 }
    }

    @Nested
    inner class NavigateToProfileContext {
        @BeforeEach
        fun setup() {
            manager.navigateToProfile()
        }

        @Test
        fun `Should have Profile`() {
            assertTrue { manager.backStack.value.last() is Destination.Profile }
            assertTrue { manager.backStack.value.size == 2 }
        }

        @Test
        fun `Back leaves only Home`() {
            manager.back()
            assertTrue { manager.backStack.value.last() is Destination.Home }
            assertTrue { manager.backStack.value.size == 1 }
        }

        @Test
        fun `Navigate to profile does not work`() {
            manager.navigateToProfile()
            assertTrue { manager.backStack.value.last() is Destination.Profile }
            assertEquals(2, manager.backStack.value.size)
        }
    }

    @Nested
    inner class NavigateToProductFolderContext {
        private val folder = ProductFolder(id, name, template, emptyList(), emptyList())

        @BeforeEach
        fun setup() {
            manager.navigateToProductFolder(folder)
        }

        @Test
        fun `When navigate to folder, add Folder`() {
            val last = manager.backStack.value.last() as Destination.ProductFolder
            assertTrue { last.productFolder == folder }
            assertEquals(2, manager.backStack.value.size)
        }

        @Nested
        inner class NavigateToProductFolder2Context {
            private val folder2 = ProductFolder(Id(2), name, template, emptyList(), emptyList())

            @BeforeEach
            fun setup() {
                manager.navigateToProductFolder(folder2)
            }

            @Test
            fun `Allow multiple folders in backstack`() {
                val last = manager.backStack.value.last() as Destination.ProductFolder
                assertTrue { last.productFolder == folder2 }
                assertEquals(3, manager.backStack.value.size)
            }

            @Test
            fun `Back navigates to Folder`() {
                manager.back()
                val last = manager.backStack.value.last() as Destination.ProductFolder
                assertTrue { last.productFolder === folder }
                assertTrue { manager.backStack.value.size == 2 }
            }
        }

        @Test
        fun `Does not allow similar folders in backstack`() {
            val last = manager.backStack.value.last() as Destination.ProductFolder
            manager.navigateToProductFolder(folder)
            assertTrue { last.productFolder == folder }
            assertEquals(2, manager.backStack.value.size)
        }

        @Test
        fun `Navigate to home leaves only Home`() {
            manager.navigateToHome()
            assertTrue { manager.backStack.value.last() is Destination.Home }
            assertTrue { manager.backStack.value.size == 1 }
        }
    }

    @Nested
    inner class NavigateToCatalogContext {
        private val catalog = Catalog(id, name, emptyList())

        @BeforeEach
        fun setup() {
            manager.navigateToCatalog(catalog)
        }

        @Test
        fun `When navigate to folder, add Folder`() {
            val last = manager.backStack.value.last() as Destination.Catalog
            assertTrue { last.catalog == catalog }
            assertEquals(2, manager.backStack.value.size)
        }

        @Nested
        inner class NavigateToCatalog2Context {
            private val catalog2 = Catalog(Id(2), name, emptyList())

            @BeforeEach
            fun setup() {
                manager.navigateToCatalog(catalog2)
            }

            @Test
            fun `Allow multiple folders in backstack`() {
                val last = manager.backStack.value.last() as Destination.Catalog
                assertTrue { last.catalog == catalog2 }
                assertEquals(3, manager.backStack.value.size)
            }

            @Test
            fun `Back navigates to Folder`() {
                manager.back()
                val last = manager.backStack.value.last() as Destination.Catalog
                assertTrue { last.catalog === catalog }
                assertTrue { manager.backStack.value.size == 2 }
            }
        }

        @Test
        fun `Does not allow similar folders in backstack`() {
            val last = manager.backStack.value.last() as Destination.Catalog
            manager.navigateToCatalog(catalog)
            assertTrue { last.catalog == catalog }
            assertEquals(2, manager.backStack.value.size)
        }

        @Test
        fun `Navigate to home leaves only Home`() {
            manager.navigateToHome()
            assertTrue { manager.backStack.value.last() is Destination.Home }
            assertTrue { manager.backStack.value.size == 1 }
        }
    }

    @Nested
    inner class NavigateToNotifications {
        @BeforeEach
        fun setup() {
            manager.navigateToNotifications()
        }

        @Test
        fun `Should have Notifications`() {
            val last = manager.backStack.value.last()
            assertTrue { last is Destination.Notifications }
        }

        @Test
        fun `Does not allow duplicate Notifications`() {
            manager.navigateToNotifications()
            val backStack = manager.backStack.value
            assertEquals(1, backStack.count { it is Destination.Notifications })
        }
    }

    @Test
    fun `When navigate to add template, should have add template`() {
        manager.navigateToAddTemplate()
        assertTrue { manager.backStack.value.last() is Destination.AddTemplate }
        assertTrue { manager.backStack.value.size == 2 }
    }
}