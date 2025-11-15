package com.orka.myfinances.testLib

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import org.junit.jupiter.api.Assertions.assertTrue

suspend inline fun <T, reified TLoading: T> Flow<T>.assertLoadingTransition(crossinline action: () -> Unit) = test {
    awaitItem()
    action()
    val state = awaitItem()
    awaitItem()

    assertTrue { state is TLoading }
}