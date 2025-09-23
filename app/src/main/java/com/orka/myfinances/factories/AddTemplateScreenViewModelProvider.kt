package com.orka.myfinances.factories

import com.orka.myfinances.ui.screens.add.template.AddTemplateScreenViewModel

fun interface AddTemplateScreenViewModelProvider {
    fun addTemplateViewModel(): AddTemplateScreenViewModel
}