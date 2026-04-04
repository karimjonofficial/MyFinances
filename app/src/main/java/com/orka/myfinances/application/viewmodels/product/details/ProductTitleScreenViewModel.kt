package com.orka.myfinances.application.viewmodels.product.details

import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.toApiRequest
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.format.FormatDate
import com.orka.myfinances.lib.format.FormatDecimal
import com.orka.myfinances.lib.format.FormatPrice
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
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
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<ProductTitleApiModel, ProductTitleScreenModel>(
    id = productId,
    get = { productTitleApi.getById(it) },
    map = { it.toScreenModel(formatDecimal, formatDate, formatPrice) },
    loading = loading,
    failure = failure,
    logger = logger
), ProductTitleScreenInteractor {
    val uiState = state.asStateFlow()
    private lateinit var categoryId: Id

    init {
        initialize()
    }

    override fun receive(amount: Int, totalPrice: Int, comment: String?) {
        launch {
            val old = state.value//TODO kill it before does you
            setState(State.Loading(loading))
            val title = productTitleApi.getById<ProductTitleApiModel?>(productId)
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
            val created = receiveApi.insert(
                request = request,
                map = AddReceiveRequest::toApiRequest
            )
            if(created) flow.emit(StockEvent(categoryId))
            setState(old)
        }
    }
}