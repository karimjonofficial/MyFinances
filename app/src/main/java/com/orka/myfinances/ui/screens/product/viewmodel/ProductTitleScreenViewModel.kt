package com.orka.myfinances.ui.screens.product.viewmodel

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.ReceiveItemModel
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class ProductTitleScreenViewModel(
    private val id: Id,
    private val client: HttpClient,
    private val formatDecimal: FormatDecimal,
    private val formatDate: FormatDate,
    private val formatPrice: FormatPrice,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    fun receive(
        price: Int,
        salePrice: Int,
        amount: Int,
        totalPrice: Int,
        comment: String?
    ) {
        launch {
            val request = AddReceiveRequest(
                items = listOf(
                    ReceiveItemModel(
                        productTitleId = id,
                        price = price,
                        salePrice = salePrice,
                        amount = amount
                    )
                ),
                price = totalPrice,
                comment = comment
            )
            try {
                val response = client.post(
                    urlString = "receives/",
                    block = { setBody(request) }
                )
                if (response.status == HttpStatusCode.Created) {
                    // Success logic if needed
                }
            } catch (_: Exception) {
                // Handle error
            }
        }
    }

    override fun initialize() {
        launch {
            try {
                val response = client.get("product-titles/${id.value}/")
                if (response.status == HttpStatusCode.OK) {
                    val productTitle = response.body<ProductTitle>()
                    val title = productTitle.toModel(formatDecimal, formatDate, formatPrice)
                    setState(State.Success(title))
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }
}
