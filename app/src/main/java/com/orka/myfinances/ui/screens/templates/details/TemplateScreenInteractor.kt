package com.orka.myfinances.ui.screens.templates.details

interface TemplateScreenInteractor {
    fun back()

    companion object {
        val dummy = object : TemplateScreenInteractor {
            override fun back() {}
        }
    }
}