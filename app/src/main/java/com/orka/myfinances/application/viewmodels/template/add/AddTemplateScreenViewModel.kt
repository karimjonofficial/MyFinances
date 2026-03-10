package com.orka.myfinances.application.viewmodels.template.add

import com.orka.myfinances.data.api.template.TemplateApi
import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.lib.viewmodel.Manager
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.add.AddTemplateInteractor

class AddTemplateScreenViewModel(
    private val templateApi: TemplateApi,
    private val navigator: Navigator
) : Manager(), AddTemplateInteractor {

    override fun addTemplate(template: AddTemplateRequest) {
        launch {
            if (templateApi.add(template)) {
                navigator.back()
            }
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}