package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.receive.toApiRequest
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.toEntity
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.data.repositories.stock.StockEvent
import com.orka.myfinances.lib.data.api.scoped.office.add
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFulViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenInteractor
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AddReceiveScreenViewModel(
    private val categoryId: Id,
    private val folderApi: FolderApi,
    private val productTitleApi: ProductTitleApi,
    private val receiveApi: ReceiveApi,
    private val navigator: Navigator,
    private val flow: MutableSharedFlow<StockEvent>,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : StateFulViewModel<State<AddReceiveScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), AddReceiveScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            try {
                val category = folderApi.getById(categoryId.value)?.map() as? Category
                if (category != null) {
                    val titlesModels = productTitleApi.getByCategory(categoryId.value)
                    if (titlesModels != null) {
                        val titles = titlesModels.map { it.toEntity(category) }
                        setState(State.Success(AddReceiveScreenModel(category, titles)))
                    } else {
                        setState(State.Failure(failure))
                    }
                } else {
                    setState(State.Failure(failure))
                }
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val category = folderApi.getById(categoryId.value)?.map() as? Category
                if (category != null) {
                    val titlesModels = productTitleApi.getByCategory(categoryId.value)
                    if (titlesModels != null) {
                        val titles = titlesModels.map { it.toEntity(category) }
                        setState(State.Success(AddReceiveScreenModel(category, titles)))
                    } else {
                        setState(State.Failure(failure))
                    }
                } else {
                    setState(State.Failure(failure))
                }
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun add(
        title: ProductTitle?,
        amount: Int?,
        price: Int?,
        salePrice: Int?,
        totalPrice: Int?,
        description: String?
    ) {
        launch {
            if (
                title != null && amount != null && amount > 0 &&
                price != null && price > 0 &&
                salePrice != null && salePrice > 0 &&
                totalPrice != null
            ) {
                val request = AddReceiveRequest(
                    items = listOf(
                        AddReceiveRequestItem(
                            productTitleId = title.id,
                            amount = amount,
                            price = price,
                            salePrice = salePrice,
                            description = description
                        )
                    ),
                    price = totalPrice
                )
                val created = receiveApi.add(
                    request = request,
                    map = AddReceiveRequest::toApiRequest
                )
                if (created) {
                    flow.emit(StockEvent(categoryId))
                    navigator.back()
                }
            }
        }
    }
}