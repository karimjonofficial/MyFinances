package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.data.repositories.template.AddTemplateRequest

interface AddTemplateScreenInteractor {
    fun addTemplate(request: AddTemplateRequest)
    fun back()

    companion object {
        val dummy = object : AddTemplateScreenInteractor {
            override fun addTemplate(request: AddTemplateRequest) {}
            override fun back() {}
        }
    }
}