package com.orka.myfinances.ui.screens.template

import com.orka.myfinances.core.Manager
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TemplateScreenViewModel(
    private val repository: TemplateRepository,
    context: CoroutineContext = Dispatchers.Default
) : Manager(context) {

    fun addTemplate(template: AddTemplateRequest) = launch {
        repository.add(template)
    }
}