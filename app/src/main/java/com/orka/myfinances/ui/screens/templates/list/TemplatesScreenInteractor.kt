package com.orka.myfinances.ui.screens.templates.list

import com.orka.myfinances.lib.ui.viewmodel.ChunkViewModel
import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface TemplatesScreenInteractor : StateFul, ChunkViewModel {
    fun addTemplate()
    fun select(template: TemplateUiModel)

    companion object {
        val dummy = object : TemplatesScreenInteractor {
            override fun addTemplate() {}
            override fun select(template: TemplateUiModel) {}
            override fun initialize() {}
            override fun refresh() {}
            override fun loadMore() {}
        }
    }
}