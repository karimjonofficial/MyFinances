package com.orka.myfinances.ui.screens.templates.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.template.Template
import com.orka.myfinances.data.api.template.TemplateApiModel
import com.orka.myfinances.data.repositories.template.TemplateEvent
import com.orka.myfinances.data.api.template.map
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow

class TemplatesScreenViewModel(
    private val client: HttpClient,
    private val events: Flow<TemplateEvent>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<TemplatesScreenState>(
    initialState = TemplatesScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    init {
        launch {
            events.collect {
                initialize()
            }
        }
    }

    override fun initialize() {
        launch {
            try {
                val response = client.get("templates/")
                if (response.status == HttpStatusCode.OK) {
                    val templates = response.body<List<TemplateApiModel>>().map { it.map() }
                    updateState { TemplatesScreenState.Success(templates.map { it.toUiModel() }) }
                } else {
                    updateState { TemplatesScreenState.Error }
                }
            } catch (_: Exception) {
                updateState { TemplatesScreenState.Error }
            }
        }
    }

    fun addTemplate() {
        launch {
            navigator.navigateToAddTemplate()
        }
    }

    fun select(template: Template) {
        launch {
            navigator.navigateToTemplate(template.id)
        }
    }
}
