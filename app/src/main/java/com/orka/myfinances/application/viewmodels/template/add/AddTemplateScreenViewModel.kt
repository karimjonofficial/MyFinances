package com.orka.myfinances.application.viewmodels.template.add

import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.api.template.toApiRequest
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.TemplateFieldModel
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.viewmodel.Manager
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenInteractor

class AddTemplateScreenViewModel(
    private val templateApi: TemplateApi,
    private val navigator: Navigator
) : Manager(), AddTemplateScreenInteractor {
    override fun addTemplate(name: String, fields: List<TemplateFieldModel>) {
        launch {
            val request = AddTemplateRequest(name, fields)
            val created = templateApi.insert(
                request = request,
                map = AddTemplateRequest::toApiRequest
            )
            if (created) navigator.back()
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}