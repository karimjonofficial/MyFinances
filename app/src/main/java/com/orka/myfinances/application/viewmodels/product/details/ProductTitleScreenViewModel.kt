package com.orka.myfinances.application.viewmodels.product.details

import androidx.lifecycle.viewModelScope
import com.orka.myfinances.data.dtos.product.title.ProductTitleDto
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.format.FormatDate
import com.orka.myfinances.format.FormatDecimal
import com.orka.myfinances.format.FormatPrice
import com.orka.myfinances.lib.data.repositories.GetById
import com.orka.myfinances.lib.data.repositories.Insert
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.lib.viewmodel.sourceful.single.MapSingleByIdViewModel
import com.orka.myfinances.logger.Logger
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.details.ProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.details.models.ProductTitleScreenModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProductTitleScreenViewModel(
    private val productId: Id,
    private val getById: GetById<ProductTitleDto>,
    private val insertReceive: Insert<AddReceiveRequest>,
    productTitleEvents: Flow<ProductTitleEvent>,
    private val formatDecimal: FormatDecimal,
    private val formatDate: FormatDate,
    private val formatPrice: FormatPrice,
    private val navigator: Navigator,
    logger: Logger
) : MapSingleByIdViewModel<ProductTitleDto, ProductTitleScreenModel>(
    id = productId,
    get = getById,
    map = { it.toScreenModel(formatDecimal, formatDate, formatPrice) },
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
        tryTransition { oldState ->
                val title = getById.getById(productId)
                if (title == null)
                    State.Failure(ProductTitleNotFound, oldState.value)
                else {
                    categoryId = Id(title.category)
                    val price = title.defaultPrice.toInt()
                    val salePrice = title.defaultSalePrice.toInt()
                    val exposedPrice = title.defaultExposedPrice.toInt()
                    val item = AddReceiveRequestItem(
                        productTitleId = productId,
                        price = price,
                        salePrice = salePrice,
                        exposedPrice = exposedPrice,
                        amount = amount
                    )
                    val request = AddReceiveRequest(
                        categoryId = categoryId,
                        items = listOf(item),
                        price = totalPrice,
                        comment = comment
                    )
                    val created = insertReceive.insert(request)
                    if (created) oldState
                    else State.Failure(NotInserted, oldState.value)
                }
        }
    }
}