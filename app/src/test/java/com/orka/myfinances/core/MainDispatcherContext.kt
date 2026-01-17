package com.orka.myfinances.core

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class MainDispatcherContext {
    val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    val testScope: TestScope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun teardown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun advanceUntilIdle() {
        testScope.advanceUntilIdle()
    }

    fun runTest(body: suspend TestScope.() -> Unit) = testScope.runTest(testBody = body)

    suspend fun runAndAdvance(action: suspend () -> Unit) {
        action()
        advanceUntilIdle()
    }

    fun runAndCancelChildren(action: suspend () -> Unit) = testScope.runTest {
        action()
        testScope.coroutineContext.cancelChildren()
    }

    fun launch(body: suspend () -> Unit) = testScope.launch { body() }

    fun cancelChildren() {
        testScope.coroutineContext.cancelChildren()
    }
}