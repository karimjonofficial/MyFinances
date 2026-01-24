package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.core.Manager
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.lib.data.repositories.AddRepository
import kotlinx.coroutines.CoroutineScope

class AddTemplateScreenViewModel(
    private val repository: AddRepository<Template, AddTemplateRequest>,
    coroutineScope: CoroutineScope
) : Manager(coroutineScope) {

    fun addTemplate(template: AddTemplateRequest) = launch {
        repository.add(template)
    }
}