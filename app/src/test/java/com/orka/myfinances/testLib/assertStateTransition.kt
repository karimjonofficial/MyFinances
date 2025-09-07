package com.orka.myfinances.testLib

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun <T> TestScope.assertStateTransition(
    stateFlow: StateFlow<T>,
    assertState: (T) -> Boolean,
    skippedSameTransitions: Int = 0,
    action: () -> Unit,
) = this.runTest {
    var count = 0
    var result: T? = null

    val job = this.launch(UnconfinedTestDispatcher(testScheduler)) {
        stateFlow.collect {
            this@runTest.advanceUntilIdle()
            println("Collecting: $it")

            if (assertState(it)) {
                println("Counting for: $it")
                result = it
                count++
                if(count == skippedSameTransitions + 1) {
                    println("Expected $skippedSameTransitions matching emissions reached. Cancelling collection.")
                    this@launch.cancel()
                }
            }
        }
    }

    action()
    delay(100)
    job.join()
    this.advanceUntilIdle()

    println("Assert: $result")
    println("Final count: $count")
    assertEquals(1 + skippedSameTransitions, count)
}