package com.orka.myfinances.ui.screens.sale.viewmodel

import android.util.Log
import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.sale.SaleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.format.FormatDateTime
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class SaleScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val formatPrice: FormatPrice,
    private val formatDateTime: FormatDateTime,
    private val formatDecimal: FormatDecimal,
    private val navigator: Navigator,
    private val loading: UiText,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), SaleInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            setState(State.Loading(loading))
            try {
                val response = client.get("sales/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val sale = response.body<SaleApiModel>()
                    val model = sale.map(formatPrice, formatDateTime, formatDecimal)
                    setState(State.Success(model))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (e: Exception) {
                Log.d("SaleScreenViewModel", "Failure: $e")
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun navigateToClient(clientId: Id) {
        launch {
            navigator.navigateToClient(clientId)
        }
    }

    override fun back() {
        launch {
            navigator.back()
        }
    }
}
