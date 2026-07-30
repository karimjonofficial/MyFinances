package com.orka.myfinances.application.viewmodels.template.add

import com.orka.myfinances.data.repositories.template.requests.AddTemplateRequest
import com.orka.myfinances.data.repositories.template.requests.TemplateFieldModel
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.viewmodel.manager.Manager
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.templates.add.AddTemplateScreenInteractor

class AddTemplateScreenViewModel(
    private val insert: Insert<AddTemplateRequest>,
    private val navigator: Navigator,
    logger: Logger
) : Manager(logger), AddTemplateScreenInteractor {

    override fun addTemplate(name: String, fields: List<TemplateFieldModel>) {
        launch {
            val request = AddTemplateRequest(name, fields)
            val created = insert.insert(request)
            if (created) navigator.back()
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}