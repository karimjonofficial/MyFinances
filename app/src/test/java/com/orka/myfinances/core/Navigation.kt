package com.orka.myfinances.core

import com.orka.myfinances.ui.navigation.NavigationManager
import com.orka.myfinances.ui.navigation.Destination
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue

inline fun <reified T: Destination> NavigationManager.assertTopIs(message: String = "") {
    val last = backStack.value.last()
    assertTrue(last is T, "The current destination is $last\n$message")
}

fun NavigationManager.assertSize(size: Int) {
    assertEquals(size, backStack.value.size)
}

fun NavigationManager.test(body: NavigationManager.() -> Unit) {
    return body()
}

fun NavigationManager.assertHome() {
    assertTopIs<Destination.Home>()
    assertSize(1)
}