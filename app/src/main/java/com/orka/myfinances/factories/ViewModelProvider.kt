package com.orka.myfinances.factories

import com.orka.myfinances.ui.screens.template.TemplateScreenViewModel

class ViewModelProvider(private val templateScreenViewModel: TemplateScreenViewModel) {
    fun templateScreenViewModel(): TemplateScreenViewModel {
        return templateScreenViewModel
    }
}