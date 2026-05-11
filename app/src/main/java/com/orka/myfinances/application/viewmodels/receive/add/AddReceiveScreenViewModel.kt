package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.models.response.FolderApiModel
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.toApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.MapSingleViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenInteractor
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenModel
import com.orka.myfinances.ui.screens.receive.add.ProductTitleItemModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AddReceiveScreenViewModel(
    private val categoryId: Id,
    private val folderApi: FolderApi,
    private val receiveApi: ReceiveApi,
    private val navigator: Navigator,
    private val flow: MutableSharedFlow<StockEvent>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : MapSingleViewModel<FolderApiModel, AddReceiveScreenModel>(
    id = categoryId,
    loading = loading,
    failure = failure,
    get = { folderApi.getById(it.value) },
    map = { it.toScreenModel() },
    logger = logger
), AddReceiveScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun add(
        title: ProductTitleItemModel?,
        amount: Int?,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        totalPrice: Int?,
        description: String?
    ) {
        launch {
            val oldState = state.value
            if (
                title != null && amount != null && amount > 0 &&
                price != null && price > 0 &&
                salePrice != null && salePrice > price &&
                exposedPrice != null && exposedPrice > salePrice &&
                totalPrice != null
            ) {
                setState(State.Loading(loading, oldState.value))
                val request = AddReceiveRequest(
                    items = listOf(
                        AddReceiveRequestItem(
                            productTitleId = title.id,
                            amount = amount,
                            price = price,
                            salePrice = salePrice,
                            exposedPrice = exposedPrice,
                            description = description
                        )
                    ),
                    price = totalPrice
                )
                val created = receiveApi.insert(
                    request = request,
                    map = AddReceiveRequest::toApiRequest
                )
                if (created) {
                    flow.emit(StockEvent(categoryId))
                    navigator.back()
                } else setState(State.Failure(failure, oldState.value))
            }
        }
    }
}
