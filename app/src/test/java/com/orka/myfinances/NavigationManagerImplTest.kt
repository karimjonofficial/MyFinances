package com.orka.myfinances

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.data.models.folder.Warehouse
import com.orka.myfinances.factories.ViewModelProviderImpl
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.testLib.id
import com.orka.myfinances.testLib.name
import com.orka.myfinances.testLib.template
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val templateRepository = DummyTemplateRepository()
    private val addTemplateScreenViewModel = AddTemplateScreenViewModel(templateRepository)
    private val templatesScreenViewModel = TemplatesScreenViewModel(templateRepository, logger)
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger, coroutineContext)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
    private val provider = ViewModelProviderImpl(addTemplateScreenViewModel, templatesScreenViewModel, homeScreenViewModel)
    private val navigationManager = NavigationManagerImpl(initialBackStack, provider, logger, coroutineContext)

    @Test
    fun nothing() {
        assertTrue { navigationManager.backStack.value.first() is Destination.Home }
    }

    @Test
    fun `When only home left, back does not work`() {
        navigationManager.back()
        assertTrue { navigationManager.backStack.value.first() is Destination.Home }
        assertTrue { navigationManager.backStack.value.size == 1 }
    }

    @Test
    fun `When only home left, navigate to home does not work`() {
        navigationManager.navigateToHome()
        assertTrue { navigationManager.backStack.value.first() is Destination.Home }
        assertTrue { navigationManager.backStack.value.size == 1 }
    }

    @Nested
    inner class NavigateToProfileContext {
        @BeforeEach
        fun setup() {
            navigationManager.navigateToProfile()
        }

        @Test
        fun `Should have Profile`() {
            assertTrue { navigationManager.backStack.value.last() is Destination.Profile }
            assertTrue { navigationManager.backStack.value.size == 2 }
        }

        @Test
        fun `Back leaves only Home`() {
            navigationManager.back()
            assertTrue { navigationManager.backStack.value.last() is Destination.Home }
            assertTrue { navigationManager.backStack.value.size == 1 }
        }

        @Test
        fun `Navigate to profile does not work`() {
            navigationManager.navigateToProfile()
            assertTrue { navigationManager.backStack.value.last() is Destination.Profile }
            assertEquals(2, navigationManager.backStack.value.size)
        }

        @Test
        fun `When navigate to templates, and go back leaves Profile`() {
            navigationManager.navigateToTemplates()
            navigationManager.back()
            assertTrue { navigationManager.backStack.value.last() is Destination.Profile }
            assertTrue { navigationManager.backStack.value.size == 2 }
        }
    }

    @Nested
    inner class NavigateToProductFolderContext {
        private val folder = Warehouse(id, name, template, emptyList(), emptyList())

        @BeforeEach
        fun setup() {
            navigationManager.navigateToProductFolder(folder)
        }

        @Test
        fun `When navigate to folder, add Folder`() {
            val last = navigationManager.backStack.value.last() as Destination.Warehouse
            assertTrue { last.warehouse == folder }
            assertEquals(2, navigationManager.backStack.value.size)
        }

        @Nested
        inner class NavigateToProductFolder2Context {
            private val folder2 = Warehouse(Id(2), name, template, emptyList(), emptyList())

            @BeforeEach
            fun setup() {
                navigationManager.navigateToProductFolder(folder2)
            }

            @Test
            fun `Allow multiple folders in backstack`() {
                val last = navigationManager.backStack.value.last() as Destination.Warehouse
                assertTrue { last.warehouse == folder2 }
                assertEquals(3, navigationManager.backStack.value.size)
            }

            @Test
            fun `Back navigates to Folder`() {
                navigationManager.back()
                val last = navigationManager.backStack.value.last() as Destination.Warehouse
                assertTrue { last.warehouse === folder }
                assertTrue { navigationManager.backStack.value.size == 2 }
            }
        }

        @Test
        fun `Does not allow similar folders in backstack`() {
            val last = navigationManager.backStack.value.last() as Destination.Warehouse
            navigationManager.navigateToProductFolder(folder)
            assertTrue { last.warehouse == folder }
            assertEquals(2, navigationManager.backStack.value.size)
        }

        @Test
        fun `Navigate to home leaves only Home`() {
            navigationManager.navigateToHome()
            assertTrue { navigationManager.backStack.value.last() is Destination.Home }
            assertTrue { navigationManager.backStack.value.size == 1 }
        }
    }

    @Nested
    inner class NavigateToCatalogContext {
        private val catalog = Catalog(id, name, emptyList())

        @BeforeEach
        fun setup() {
            navigationManager.navigateToCatalog(catalog)
        }

        @Test
        fun `When navigate to folder, add Folder`() {
            val last = navigationManager.backStack.value.last() as Destination.Catalog
            assertTrue { last.catalog == catalog }
            assertEquals(2, navigationManager.backStack.value.size)
        }

        @Nested
        inner class NavigateToCatalog2Context {
            private val catalog2 = Catalog(Id(2), name, emptyList())

            @BeforeEach
            fun setup() {
                navigationManager.navigateToCatalog(catalog2)
            }

            @Test
            fun `Allow multiple folders in backstack`() {
                val last = navigationManager.backStack.value.last() as Destination.Catalog
                assertTrue { last.catalog == catalog2 }
                assertEquals(3, navigationManager.backStack.value.size)
            }

            @Test
            fun `Back navigates to Folder`() {
                navigationManager.back()
                val last = navigationManager.backStack.value.last() as Destination.Catalog
                assertTrue { last.catalog === catalog }
                assertTrue { navigationManager.backStack.value.size == 2 }
            }
        }

        @Test
        fun `Does not allow similar folders in backstack`() {
            val last = navigationManager.backStack.value.last() as Destination.Catalog
            navigationManager.navigateToCatalog(catalog)
            assertTrue { last.catalog == catalog }
            assertEquals(2, navigationManager.backStack.value.size)
        }

        @Test
        fun `Navigate to home leaves only Home`() {
            navigationManager.navigateToHome()
            assertTrue { navigationManager.backStack.value.last() is Destination.Home }
            assertTrue { navigationManager.backStack.value.size == 1 }
        }
    }

    @Nested
    inner class NavigateToNotifications {
        @BeforeEach
        fun setup() {
            navigationManager.navigateToNotifications()
        }

        @Test
        fun `Should have Notifications`() {
            val last = navigationManager.backStack.value.last()
            assertTrue { last is Destination.Notifications }
        }

        @Test
        fun `Does not allow duplicate Notifications`() {
            navigationManager.navigateToNotifications()
            val backStack = navigationManager.backStack.value
            assertEquals(1, backStack.count { it is Destination.Notifications })
        }
    }

    @Test
    fun `When navigate to add template, should have add template`() {
        navigationManager.navigateToAddTemplate()
        assertTrue { navigationManager.backStack.value.last() is Destination.AddTemplate }
        assertTrue { navigationManager.backStack.value.size == 2 }
    }

    @Nested
    inner class NavigateToSettingsContext {
        @BeforeEach
        fun setup() {
            navigationManager.navigateToSettings()
        }

        @Test
        fun `Should have Settings`() {
            assertTrue { navigationManager.backStack.value.last() is Destination.Settings }
            assertTrue { navigationManager.backStack.value.size == 2 }
        }

        @Test
        fun `Back leaves only Home`() {
            navigationManager.back()
            assertTrue { navigationManager.backStack.value.last() is Destination.Home }
            assertTrue { navigationManager.backStack.value.size == 1 }
        }

        @Test
        fun `Navigate to settings does not work`() {
            navigationManager.navigateToSettings()
            assertTrue { navigationManager.backStack.value.last() is Destination.Settings }
            assertEquals(2, navigationManager.backStack.value.size)
        }
    }

    @Nested
    inner class NavigateToTemplatesContext {
        @BeforeEach
        fun setup() {
            navigationManager.navigateToTemplates()
        }

        @Test
        fun `Should add Templates`() {
            assertTrue { navigationManager.backStack.value.last() is Destination.Templates }
            assertTrue { navigationManager.backStack.value.size == 2 }
        }

        @Test
        fun `Has the same ViewModel`() {
            val state = navigationManager.backStack.value.last() as Destination.Templates
            assertEquals(templatesScreenViewModel, state.viewModel)
        }

        @Test
        fun `Does not allow duplicate templates`() {
            navigationManager.navigateToTemplates()
            assertTrue { navigationManager.backStack.value.last() is Destination.Templates }
            assertEquals(2, navigationManager.backStack.value.size)
        }
    }
}