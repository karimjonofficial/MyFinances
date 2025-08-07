package com.orka.myfinances.testLib

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> TestScope.assertStateTransition(
    stateFlow: StateFlow<T>,
    assertState: (T) -> Boolean,
    skippedDesiredTransitions: Int = 0,
    action: () -> Unit,
) = this.runTest {
    var count = 0
    var result: T? = null

    val job = this.launch {
        stateFlow.collect {
            println("Collect: $it")
            if (assertState(it)) {
                println("Add: $it")
                result = it
                count++
            }
        }
    }

    action()
    this.advanceUntilIdle()
    job.cancel()

    println("Assert: $result")
    assertNotNull(result)
    println("Final count: $count")
    assertEquals(1 + skippedDesiredTransitions, count)
}