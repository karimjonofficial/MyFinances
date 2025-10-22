package com.orka.myfinances.fixtures

import com.orka.myfinances.factories.ViewModelProvider

class ViewModelProviderStub : ViewModelProvider {
    override fun homeViewModel(): Any {
        return "home"
    }

    override fun templatesViewModel(): Any {
        return "templates"
    }

    override fun addTemplateViewModel(): Any {
        return "add template"
    }

    override fun addProductViewModel(): Any {
        return "add product"
    }
}