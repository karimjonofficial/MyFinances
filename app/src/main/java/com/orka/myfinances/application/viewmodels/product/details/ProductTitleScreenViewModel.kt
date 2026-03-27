package com.orka.myfinances.application.viewmodels.product.details

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFul
import com.orka.myfinances.ui.screens.product.details.ProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.details.models.ProductTitleScreenModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductTitleScreenViewModel(
    private val productId: Id,
    private val receiveApi: ReceiveApi,
    private val productTitleApi: ProductTitleApi,
    private val formatDecimal: FormatDecimal,
    private val formatDate: FormatDate,
    private val formatPrice: FormatPrice,
    private val flow: MutableSharedFlow<StockEvent>,
    private val loading: UiText,
    logger: Logger
) : StateFul<State<ProductTitleScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), ProductTitleScreenInteractor {
    val uiState = state.asStateFlow()
    private lateinit var categoryId: Id

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            try {
                val productTitle = productTitleApi.getById(productId.value)
                if (productTitle != null) {
                    val title = productTitle.toModel(formatDecimal, formatDate, formatPrice)
                    categoryId = Id(productTitle.category)
                    setState(State.Success(title))
                } else setState(State.Failure(UiText.Res(R.string.failure)))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun receive(amount: Int, totalPrice: Int, comment: String?) {
        launch {
            val old = state.value//TODO kill it before does you
            setState(State.Loading(loading))
            val title = productTitleApi.getById(productId.value)
            val price = title?.defaultPrice?.toInt()
            val salePrice = title?.defaultSalePrice?.toInt()
            val request = AddReceiveRequest(
                items = listOf(
                    AddReceiveRequestItem(
                        productTitleId = productId,
                        price = price,
                        salePrice = salePrice,
                        amount = amount
                    )
                ),
                price = totalPrice,
                comment = comment
            )
            val created = receiveApi.add(request)
            if(created) flow.emit(StockEvent(categoryId))
            setState(old)
        }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val productTitle = productTitleApi.getById(productId.value)
                if (productTitle != null) {
                    val title = productTitle.toModel(formatDecimal, formatDate, formatPrice)
                    categoryId = Id(productTitle.category)
                    setState(State.Success(title))
                } else setState(State.Failure(UiText.Res(R.string.failure)))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}