package com.orka.myfinances.ui.screens.templates.details

interface TemplateScreenInteractor {
    fun initialize()
    fun back()

    companion object {
        val dummy = object : TemplateScreenInteractor {
            override fun initialize() {}
            override fun back() {}
        }
    }
}