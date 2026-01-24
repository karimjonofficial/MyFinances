package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.core.Manager
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.lib.data.repositories.AddRepository

class AddTemplateScreenViewModel(
    private val repository: AddRepository<Template, AddTemplateRequest>
) : Manager() {

    fun addTemplate(template: AddTemplateRequest) = launch {
        repository.add(template)
    }
}