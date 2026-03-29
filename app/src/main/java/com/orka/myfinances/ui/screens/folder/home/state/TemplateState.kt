package com.orka.myfinances.ui.screens.folder.home.state

import com.orka.myfinances.ui.navigation.entries.home.TemplateItemModel

interface TemplateState {
    object Initial : TemplateState
    object Error : TemplateState
    data class Success(val templates: List<TemplateItemModel>) : TemplateState
}