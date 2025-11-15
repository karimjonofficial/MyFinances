package com.orka.myfinances.core

import com.orka.myfinances.impl.ui.managers.NavigationManagerImpl
import com.orka.myfinances.ui.managers.navigation.Destination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

inline fun <reified T: Destination> NavigationManagerImpl.assertTopIs(message: String = "") {
    val last = backStack.value.last()
    assertTrue(last is T, "The current destination is $last\n$message")
}

inline fun <reified T: Destination> NavigationManagerImpl.assertNavState() {
    assertTrue(navigationState.value is T, "Navigation Bar state error")
}

fun NavigationManagerImpl.assertSize(size: Int) {
    assertEquals(size, backStack.value.size)
}

inline fun <reified T: Destination> NavigationManagerImpl.testNavigationItems(
    navigate: NavigationManagerImpl.() -> Unit
) {
    navigate()
    assertTopIs<T>()
    assertSize(2)
    assertNavState<T>()

    navigate()
    assertTopIs<T>()
    assertSize(2)
    assertNavState<T>()
}

inline fun <reified T: Destination> NavigationManagerImpl.testSingleton(
    navigateToAnotherDestination: NavigationManagerImpl.() -> Unit = { navigateToAddTemplate() },
    navigate: NavigationManagerImpl.() -> Unit
) {
    navigateToAnotherDestination()
    val size = backStack.value.size
    navigate()
    assertTopIs<T>()
    assertSize(size + 1)

    navigate()
    assertTopIs<T>()
    assertSize(size + 1)
}

inline fun <reified T: Destination, TArg> NavigationManagerImpl.testParameterizedBehavior(
    first: TArg,
    second: TArg,
    navigate: NavigationManagerImpl.(TArg) -> Unit
) {
    val size = backStack.value.size
    navigate(first)
    assertTopIs<T>()
    assertSize(size + 1)

    navigate(first)
    assertTopIs<T>()
    assertSize(size + 1)

    navigate(second)
    assertTopIs<T>()
    assertSize(size + 2)
}

inline fun <reified TInner: Destination> NavigationManagerImpl.testTemporaryBehavior(
    navigateTemp: NavigationManagerImpl.() -> Unit
) {
    val size = backStack.value.size
    navigateTemp()
    assertTopIs<TInner>()
    assertSize(size + 1)
}

fun NavigationManagerImpl.test(body: NavigationManagerImpl.() -> Unit) {
    return body()
}

fun NavigationManagerImpl.assertHome() {
    assertTopIs<Destination.Home>()
    assertNavState<Destination.Home>()
    assertSize(1)
}