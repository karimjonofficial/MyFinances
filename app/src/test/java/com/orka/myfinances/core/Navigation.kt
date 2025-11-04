package com.orka.myfinances.core

import com.orka.myfinances.NavigationManagerImpl
import com.orka.myfinances.ui.managers.navigation.Destination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.reflect.KClass

fun assertTopIs(
    expected: KClass<out Destination>,
    size: Int? = null,
    navigationManager: NavigationManagerImpl
) {
    val last = navigationManager.backStack.value.last()
    assertTrue(expected.isInstance(last)) { "Expected ${expected.simpleName}, but was $last" }
    size?.let { assertEquals(it, navigationManager.backStack.value.size) }
}

fun testSingletonBehavior(
    navigate: () -> Unit,
    destination: KClass<out Destination>,
    navigationManager: NavigationManagerImpl
) {
    navigate()
    assertTopIs(destination, size = 2, navigationManager = navigationManager)

    navigate()
    assertTopIs(destination, size = 2, navigationManager)

    navigationManager.back()
    assertTopIs(Destination.Home::class, size = 1, navigationManager)
}

fun <T> testParameterizedBehavior(
    first: T,
    second: T,
    navigate: (T) -> Unit,
    destination: KClass<out Destination>,
    navigationManager: NavigationManagerImpl
) {
    navigate(first)
    assertTopIs(destination, size = 2, navigationManager)

    navigate(first)
    assertTopIs(destination, size = 2, navigationManager)

    navigate(second)
    assertTopIs(destination, size = 3, navigationManager)

    navigationManager.back()
    assertTopIs(destination, size = 2, navigationManager)
}

fun testTemporaryBehavior(
    openParent: () -> Unit,
    navigateTemp: () -> Unit,
    parent: KClass<out Destination>,
    temp: KClass<out Destination>,
    navigationManager: NavigationManagerImpl
) {
    val beforeParent = navigationManager.backStack.value.size
    openParent()
    val afterParent = navigationManager.backStack.value.size

    val expectedParentSize = if (afterParent > beforeParent) afterParent else beforeParent
    assertTopIs(parent, size = expectedParentSize, navigationManager)

    navigateTemp()
    assertTopIs(temp, size = expectedParentSize + 1, navigationManager)

    navigationManager.back()
    assertTopIs(parent, size = expectedParentSize, navigationManager)
}