package com.orka.myfinances.ui.screens.templates.add

import com.orka.myfinances.data.repositories.template.AddTemplateRequest
import com.orka.myfinances.lib.viewmodel.Manager
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode

class AddTemplateScreenViewModel(
    private val client: HttpClient,
    private val navigator: Navigator
) : Manager(), AddTemplateInteractor {

    override fun addTemplate(template: AddTemplateRequest) {
        launch {
            val response = client.post(
                urlString = "templates/",
                block = { setBody(template) }
            )
            if (response.status == HttpStatusCode.Created) {
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
