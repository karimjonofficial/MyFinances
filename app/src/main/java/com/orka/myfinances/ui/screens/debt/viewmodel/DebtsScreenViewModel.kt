package com.orka.myfinances.ui.screens.debt.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.DebtApiModel
import com.orka.myfinances.data.api.client.ClientApiModel
import com.orka.myfinances.data.repositories.debt.AddDebtRequest
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.viewmodel.MapViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias IMapViewModel<T> = com.orka.myfinances.lib.ui.viewmodel.MapViewModel<T>

class DebtsScreenViewModel(
    private val client: HttpClient,
    formatPrice: FormatPrice,
    formatDate: FormatDate,
    formatDateTime: FormatDateTime,
    loading: UiText,
    failure: UiText,
    logger: Logger,
    private val navigator: Navigator
) : MapViewModel<DebtApiModel, DebtUiModel>(
    loading = loading,
    failure = failure,
    get = {
        try {
            val response = client.get("merchant/debts/")
            if (response.status == HttpStatusCode.OK) {
                response.body<List<DebtApiModel>>()
            } else null
        } catch (_: Exception) {
            null
        }
    },
    map = { it.toMap(formatPrice, formatDate, formatDateTime) },
    logger = logger
), IMapViewModel<DebtUiModel>, DebtsInteractor {
    override val uiState = state.asStateFlow()

    private val _dialogState = MutableStateFlow<DialogState>(DialogState.Loading)
    val dialogState = _dialogState.asStateFlow()

    override fun add(request: AddDebtRequest) {
        launch {
            try {
                val response = client.post(
                    urlString = "merchant/debts/",
                    block = { setBody(request) }
                )
                if (response.status == HttpStatusCode.Created) {
                    initialize()
                }
            } catch (_: Exception) {
                // Handle error
            }
        }
    }

    override fun initializeClients() {
        launch {
            _dialogState.value = DialogState.Loading
            try {
                val response = client.get("clients/")
                if (response.status == HttpStatusCode.OK) {
                    response.body<List<ClientApiModel>>()
                    // Note: You'll need a way to map ClientApiModel to Client domain model here if needed for DialogState
                    // For now keeping it simple as per original logic but with ApiModel
                    // _dialogState.value = DialogState.Success(clients.map { it.toDomain() }) 
                }
            } catch (_: Exception) {
                // Handle error
            }
        }
    }

    override fun select(debt: DebtUiModel) {
        launch {
            navigator.navigateToDebt(debt.id)
        }
    }
}
