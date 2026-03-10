package com.orka.myfinances.ui.screens.templates.list

import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface TemplatesScreenInteractor : StateFul {
    fun addTemplate()
    fun select(template: Template)

    companion object {
        val dummy = object : TemplatesScreenInteractor {
            override fun addTemplate() {}
            override fun select(template: Template) {}
            override fun initialize() {}
        }
    }
}