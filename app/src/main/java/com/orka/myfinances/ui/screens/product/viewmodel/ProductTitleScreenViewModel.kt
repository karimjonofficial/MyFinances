package com.orka.myfinances.ui.screens.product.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.ReceiveItemModel
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.SingleStateViewModel
import com.orka.myfinances.lib.viewmodel.list.State
import kotlinx.coroutines.flow.asStateFlow

class ProductTitleScreenViewModel(
    private val productTitle: ProductTitle,
    private val repository: Add<Receive, AddReceiveRequest>,
    private val formatDecimal: FormatDecimal,
    private val formatDate: FormatDate,
    private val formatPrice: FormatPrice,
    logger: Logger,
    private val loading: UiText
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
) {
    val uiState = state.asStateFlow()

    fun receive(price: Int, salePrice: Int, amount: Int, totalPrice: Int, comment: String?) {
        launch {
            val request = AddReceiveRequest(
                items = listOf(
                    ReceiveItemModel(
                        productTitleId = productTitle.id,
                        price = price,
                        salePrice = salePrice,
                        amount = amount
                    )
                ),
                price = totalPrice,
                comment = comment
            )
            repository.add(request)
        }
    }

    override fun initialize() {
        setState(State.Loading(loading))
        val title = productTitle.toModel(formatDecimal, formatDate, formatPrice)
        setState(State.Success(title))
    }
}