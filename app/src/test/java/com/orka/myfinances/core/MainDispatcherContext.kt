package com.orka.myfinances.core

import com.orka.myfinances.testLib.assertStateTransition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.StateFlow
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

    fun <T> assertStateTransition(
        stateFlow: StateFlow<T>,
        assertState: (T) -> Boolean,
        skippedSameTransitions: Int = 0,
        action: () -> Unit,
    ) {
        testScope.assertStateTransition(
            stateFlow = stateFlow,
            assertState = assertState,
            skippedSameTransitions = skippedSameTransitions,
            action = action
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun runAndAdvance(action: suspend () -> Unit) {
        action()
        testScope.advanceUntilIdle()
    }

    fun runAndCancelChildren(action: suspend () -> Unit) = testScope.runTest {
        action()
        testScope.coroutineContext.cancelChildren()
    }
}