package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.datasources.category.DummyCategoryDataSource
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.datasources.category.EmptyCategoryDataSource
import com.orka.myfinances.fixtures.datasources.category.StubCategoryDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HomeScreenViewModelTest : MainDispatcherContext() {
    private val logger = DummyLogger()

    @Nested
    inner class DummyCategoryDataSourceContext {
        val dataSource = DummyCategoryDataSource()
        val viewModel = HomeScreenViewModel(logger, dataSource)

        @Test
        fun `State is Initial`() {
            val uiState = viewModel.uiState.value
            assertTrue { uiState is HomeScreenState.Initial }
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        fun `When initialize called state changes to Loading`() = testScope.runTest {
            var count = 0
            val list = mutableListOf<HomeScreenState>()
            val job = testScope.launch {
                viewModel.uiState.collect {
                    if(count == 1) list.add(it) else count++
                }
            }

            viewModel.initialize()
            testScope.advanceUntilIdle()
            job.cancel()

            assertTrue { list.isNotEmpty() }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When data source fails state goes to error`() {
        val dataSource = EmptyCategoryDataSource()
        val viewModel = HomeScreenViewModel(logger, dataSource)

        viewModel.initialize()
        testScope.advanceUntilIdle()

        assertTrue { viewModel.uiState.value is HomeScreenState.Error }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When data source successes state goes to success`() {
        val dataSource = StubCategoryDataSource()
        val viewModel = HomeScreenViewModel(logger, dataSource)

        viewModel.initialize()
        testScope.advanceUntilIdle()

        assertTrue { viewModel.uiState.value is HomeScreenState.Success }
    }
}