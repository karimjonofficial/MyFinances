package com.orka.myfinances.ui.screens.templates

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.template.TemplateApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class TemplateScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), TemplateInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val response = client.get("templates/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val template = response.body<TemplateApiModel>()
                    setState(State.Success(template))
                } else {
                    setState(State.Failure(failure))
                }
            } catch (_: Exception) {
                setState(State.Failure(failure))
            }
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}
