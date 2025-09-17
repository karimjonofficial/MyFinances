package com.orka.myfinances.factories

import com.orka.myfinances.fixtures.data.repositories.template.DummyTemplateRepository
import com.orka.myfinances.ui.screens.template.TemplateScreenViewModel
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ViewModelProviderTest {
    @Test
    fun `Returns same view model`() {
        val repository = DummyTemplateRepository()
        val viewModel = TemplateScreenViewModel(repository)
        val provider = ViewModelProvider(viewModel)
        assertEquals(viewModel, provider.templateScreenViewModel())
    }
}