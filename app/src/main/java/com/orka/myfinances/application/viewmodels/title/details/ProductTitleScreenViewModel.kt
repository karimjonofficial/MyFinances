package com.orka.myfinances.application.viewmodels.title.details

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.screens.product.details.ProductTitleScreenInteractor
import kotlinx.coroutines.flow.asStateFlow

class ProductTitleScreenViewModel(
    private val id: Id,
    private val receiveApi: ReceiveApi,
    private val productTitleApi: ProductTitleApi,
    private val formatDecimal: FormatDecimal,
    private val formatDate: FormatDate,
    private val formatPrice: FormatPrice,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), ProductTitleScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val productTitle = productTitleApi.getById(id.value)
            if (productTitle != null) {
                val title = productTitle.toModel(formatDecimal, formatDate, formatPrice)
                setState(State.Success(title))
            } else {
                setState(State.Failure(UiText.Res(R.string.failure)))
            }
        }
    }

    override fun receive(
        price: Int,
        salePrice: Int,
        amount: Int,
        totalPrice: Int,
        comment: String?
    ) {
        launch {
            val request = AddReceiveRequest(
                items = listOf(
                    AddReceiveRequestItem(
                        productTitleId = id,
                        price = price,
                        salePrice = salePrice,
                        amount = amount
                    )
                ),
                price = totalPrice,
                comment = comment
            )
            receiveApi.add(request)
        }
    }
}