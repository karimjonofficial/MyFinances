package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.data.repositories.template.TemplateFieldModel

interface AddTemplateScreenInteractor {
    fun addTemplate(name: String, fields: List<TemplateFieldModel>)
    fun back()

    companion object {
        val dummy = object : AddTemplateScreenInteractor {
            override fun addTemplate(name: String, fields: List<TemplateFieldModel>) {}
            override fun back() {}
        }
    }
}