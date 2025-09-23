package com.orka.myfinances.factories

import com.orka.myfinances.ui.screens.templates.TemplatesScreenViewModel

fun interface TemplatesScreenViewModelProvider {
    fun templatesViewModel(): TemplatesScreenViewModel
}