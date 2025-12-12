package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.core.MainDispatcherContext
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel
import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.fixtures.data.repositories.template.SpyTemplateRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AddTemplateScreenViewModelTest : MainDispatcherContext() {
    private fun viewModel(repository: TemplateRepository): AddTemplateScreenViewModel {
        return AddTemplateScreenViewModel(repository, testScope)
    }

    @Test
    fun `When add template, calls add repository`() {
        val template = AddTemplateRequest(
            name = "name",
            fields = listOf(TemplateFieldModel("name", 1))
        )
        val repository = SpyTemplateRepository()
        val viewModel = viewModel(repository)

        viewModel.addTemplate(template)
        advanceUntilIdle()

        assertTrue(repository.addCalled)
    }
}