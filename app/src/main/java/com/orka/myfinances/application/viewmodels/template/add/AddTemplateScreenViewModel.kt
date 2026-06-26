package com.orka.myfinances.application.viewmodels.template.add

import com.orka.myfinances.data.repositories.template.TemplateRepository
import com.orka.myfinances.data.repositories.template.requests.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.requests.TemplateFieldModel
import com.orka.myfinances.lib.viewmodel.Manager
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenInteractor

class AddTemplateScreenViewModel(
    private val repository: TemplateRepository,
    private val navigator: Navigator,
) : Manager(), AddTemplateScreenInteractor {

    override fun addTemplate(name: String, fields: List<TemplateFieldModel>) {
        launch {
            val request = AddTemplateRequest(name, fields)
            val created = repository.insert(request)
            if (created) navigator.back()
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}