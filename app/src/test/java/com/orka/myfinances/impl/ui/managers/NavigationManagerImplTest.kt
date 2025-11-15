package com.orka.myfinances.impl.ui.managers

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.core.assertHome
import com.orka.myfinances.core.assertSize
import com.orka.myfinances.core.assertTopIs
import com.orka.myfinances.core.test
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.repositories.folder.DummyFolderRepository
import com.orka.myfinances.fixtures.factories.SpyViewModelProvider
import com.orka.myfinances.testLib.product1
import com.orka.myfinances.ui.managers.navigation.Destination
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NavigationManagerImplTest : MainDispatcherContext() {
    private val logger = DummyLogger()
    private val folderRepository = DummyFolderRepository()
    private val homeScreenViewModel = HomeScreenViewModel(folderRepository, logger)
    private val initialBackStack = listOf(Destination.Home(homeScreenViewModel))
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
    fun `When destination is nav item, does not allow further`() = navigationManager.test {
        navigateToSettings()
        navigateToBasket()
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

    @Test
    fun `Navigate to home sets NavState Home`() = navigationManager.test {
        navigateToHome()
        assertTrue(navigationState.value is Destination.Home)
    }

    @Test
    fun `Navigate to profile sets navigation state Profile`() = navigationManager.test {
        navigateToProfile()
        assertTrue(navigationState.value is Destination.Profile)
    }

    @Test
    fun `Navigate to settings sets navigation state Settings`() = navigationManager.test {
        navigateToSettings()
        assertTrue(navigationState.value is Destination.Settings)
    }

    @Test
    fun `Navigate to basket sets navigation state Basket`() = navigationManager.test {
        navigateToBasket()
        assertTrue(navigationState.value is Destination.Basket)
    }
}