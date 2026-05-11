package com.orka.myfinances.application.viewmodels.product.details

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.toApiRequest
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.models.response.ProductTitleApiModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
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
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.details.ProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.details.models.ProductTitleScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductTitleScreenViewModel(
    private val productId: Id,
    private val receiveApi: ReceiveApi,
    private val productTitleApi: ProductTitleApi,
    productTitleEvents: Flow<ProductTitleEvent>,
    private val formatDecimal: FormatDecimal,
    private val formatDate: FormatDate,
    private val formatPrice: FormatPrice,
    private val flow: MutableSharedFlow<StockEvent>,
    private val navigator: Navigator,
    loading: UiText,
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

        productTitleEvents.onEach {
            if (it.titleId == productId) refresh()
        }.launchIn(viewModelScope)
    }

    override fun edit() {
        launch { navigator.navigateToEditProduct(productId) }
    }

    override fun receive(amount: Int, totalPrice: Int, comment: String?) {
        launch {
            val oldState = state.value
            try {
                setState(State.Loading(loading, oldState.value))
                val title = productTitleApi.getById(productId)
                if (title == null) {
                    setState(State.Failure(failure, oldState.value))
                    return@launch
                }

                categoryId = Id(title.category)
                val price = title.defaultPrice.toInt()
                val salePrice = title.defaultSalePrice.toInt()
                val exposedPrice = title.defaultExposedPrice.toInt()
                val request = AddReceiveRequest(
                    items = listOf(
                        AddReceiveRequestItem(
                            productTitleId = productId,
                            price = price,
                            salePrice = salePrice,
                            exposedPrice = exposedPrice,
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
                if (created) {
                    flow.emit(StockEvent(categoryId))
                    setState(oldState)
                } else setState(State.Failure(failure, oldState.value))
            } catch(e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}
