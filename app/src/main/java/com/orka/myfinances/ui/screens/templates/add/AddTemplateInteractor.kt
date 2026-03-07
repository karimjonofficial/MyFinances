package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.data.repositories.template.AddTemplateRequest

interface AddTemplateInteractor {
    fun addTemplate(template: AddTemplateRequest)
    fun back()
}