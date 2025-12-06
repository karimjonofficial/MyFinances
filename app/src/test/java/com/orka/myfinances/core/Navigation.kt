package com.orka.myfinances.core

import com.orka.myfinances.impl.ui.managers.NavigationManagerImpl
import com.orka.myfinances.ui.managers.navigation.Destination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

inline fun <reified T: Destination> NavigationManagerImpl.assertTopIs(message: String = "") {
    val last = backStack.value.last()
    assertTrue(last is T, "The current destination is $last\n$message")
}

fun NavigationManagerImpl.assertSize(size: Int) {
    assertEquals(size, backStack.value.size)
}

fun NavigationManagerImpl.test(body: NavigationManagerImpl.() -> Unit) {
    return body()
}

fun NavigationManagerImpl.assertHome() {
    assertTopIs<Destination.Home>()
    assertSize(1)
}