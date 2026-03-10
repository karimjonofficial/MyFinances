package com.orka.myfinances.application.viewmodels.receive.add

import com.orka.myfinances.R
import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.receive.ReceiveApi
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.map
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.AddReceiveRequestItem
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenInteractor
import com.orka.myfinances.ui.screens.receive.add.AddReceiveScreenModel
import kotlinx.coroutines.flow.asStateFlow

class AddReceiveScreenViewModel(
    private val id: Id,
    private val folderApi: FolderApi,
    private val productTitleApi: ProductTitleApi,
    private val receiveApi: ReceiveApi,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<State>(
    initialState = State.Initial,
    logger = logger
), AddReceiveScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val category = folderApi.getById(id.value)?.map() as? Category
                if (category != null) {
                    val titlesModels = productTitleApi.getByCategory(id.value)
                    if (titlesModels != null) {
                        val titles = titlesModels.map { it.map(category) }
                        setState(State.Success(AddReceiveScreenModel(category, titles)))
                    } else {
                        setState(State.Failure(UiText.Res(R.string.failure)))
                    }
                } else {
                    setState(State.Failure(UiText.Res(R.string.failure)))
                }
            } catch (_: Exception) {
                setState(State.Failure(UiText.Res(R.string.failure)))
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
                if (receiveApi.add(request)) {
                    navigator.back()
                }
            }
        }
    }
}