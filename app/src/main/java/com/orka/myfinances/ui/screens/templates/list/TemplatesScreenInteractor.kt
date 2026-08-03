package com.orka.myfinances.ui.screens.templates.list

import com.orka.myfinances.lib.ui.viewmodel.PaginatedSearchable
import com.orka.myfinances.lib.ui.viewmodel.Refreshable
import com.orka.myfinances.ui.models.ui.TemplateUiModel

interface TemplatesScreenInteractor : Refreshable, PaginatedSearchable {
    fun addTemplate()
    fun select(template: TemplateUiModel)

    companion object {
        val dummy = object : TemplatesScreenInteractor {
            override fun addTemplate() {}
            override fun select(template: TemplateUiModel) {}
            override fun refresh() {}
            override fun loadMore() {}
            override fun search(query: String) {}
            override fun resetSearch() {}
            override fun searchMore() {}
        }
    }
}
