package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.lib.viewmodel.Manager
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.lib.data.repositories.Add

class AddTemplateScreenViewModel(
    private val add: Add<Template, AddTemplateRequest>
) : Manager() {

    fun addTemplate(template: AddTemplateRequest) = launch {
        add.add(template)
    }
}