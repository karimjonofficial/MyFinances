package com.orka.myfinances.ui.screens.add.template

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel
import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.fixtures.data.repositories.template.SpyTemplateRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TemplateScreenViewModelTest : MainDispatcherContext() {

    @Test
    fun nothing() {
        val repository = DummyTemplateRepository()
        AddTemplateScreenViewModel(repository, testScope)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `When add template, calls add repository`() {
        val template = AddTemplateRequest(
            name = "name",
            fields = listOf(TemplateFieldModel("name", 1))
        )
        val repository = SpyTemplateRepository()
        val viewModel = AddTemplateScreenViewModel(repository, testScope)

        viewModel.addTemplate(template)
        testScope.advanceUntilIdle()

        Assertions.assertTrue(repository.addCalled)
    }
}