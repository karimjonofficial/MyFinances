package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.fixtures.DummyLogger
import com.orka.myfinances.fixtures.data.sources.category.DummyProductTemplateDataSource
import com.orka.myfinances.fixtures.data.sources.category.EmptyProductTemplateDataSource
import com.orka.myfinances.fixtures.data.sources.category.StubProductTemplateDataSource
import com.orka.myfinances.testLib.assertStateTransition
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HomeScreenViewModelTest : MainDispatcherContext() {
    private val context = testScope.coroutineContext
    private val logger = DummyLogger()

    @Nested
    inner class DummyCategoryDataSourceContext {
        val dataSource = DummyProductTemplateDataSource()
        val viewModel = HomeScreenViewModel(logger, dataSource, context)

        @Test
        fun `State is Initial`() {
            val uiState = viewModel.uiState.value
            assertTrue { uiState is HomeScreenState.Initial }
        }

        @Test
        fun `When initialize called state changes to Loading`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                assertState = { it is HomeScreenState.Loading },
                action = { viewModel.initialize() }
            )
        }

        @Test
        fun `When category is added state goes Loading`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.addCategory("test") },
                assertState = { it is HomeScreenState.Loading }
            )
        }
    }

    @Nested
    inner class EmptyCategoryDataSourceContext {
        val dataSource = EmptyProductTemplateDataSource()
        val viewModel = HomeScreenViewModel(logger, dataSource, context)

        @Test
        fun `When data source fails state goes to error`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.initialize() },
                assertState = { it is HomeScreenState.Error }
            )
        }

        @Test
        fun `When add category fails state goes to previous state`() {
            val state = viewModel.uiState.value

            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.addCategory("test") },
                assertState = { it === state },
                skippedDesiredTransitions = 1
            )
        }
    }

    @Nested
    inner class StubCategoryDataSourceContext {
        val dataSource = StubProductTemplateDataSource()
        val viewModel = HomeScreenViewModel(logger, dataSource, context)

        @Test
        fun `When data source successes state goes to success`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.initialize() },
                assertState = { it is HomeScreenState.Success }
            )
        }

        @Test
        fun `When add category successes state goes to success`() {
            testScope.assertStateTransition(
                stateFlow = viewModel.uiState,
                action = { viewModel.addCategory("test") },
                assertState = { it is HomeScreenState.Success }
            )
        }
    }
}