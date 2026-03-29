package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.map
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFulViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import kotlinx.coroutines.flow.asStateFlow

class AddProductTitleScreenViewModel(
    private val folderApi: FolderApi,
    private val productTitleApi: ProductTitleApi,
    private val navigator: Navigator,
    private val loading: UiText,
    private val failure: UiText,
    logger: Logger
) : StateFulViewModel<State<AddProductTitleScreenModel>>(
    initialState = State.Loading(loading),
    logger = logger
), AddProductTitleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun initialize() {
        launch {
            try {
                val categories = folderApi.getTop()?.map { it.map() }?.filterIsInstance<Category>()
                if (categories != null) {
                    setState(State.Success(AddProductTitleScreenModel(categories)))//TODO don't map to entities
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }

    override fun addProductTitle(
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int?,
        salePrice: Int?,
        description: String?,
        category: Category
    ) {
        launch {
            val p = properties.filterNotNull()

            if (
                price != null && salePrice != null &&
                p.size == category.template.fields.size && name.isNotEmpty() &&
                price > 0 && salePrice > 0 && salePrice > price &&
                category.id.value > 0
            ) {
                val request = AddProductTitleRequest(
                    category = category,
                    name = name,
                    price = price,
                    salePrice = salePrice,
                    properties = p,
                    description = description
                )
                if (productTitleApi.add(request.map())) {
                    navigator.back()
                }
            }
        }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val categories = folderApi.getTop()?.map { it.map() }?.filterIsInstance<Category>()
                if (categories != null) {
                    setState(State.Success(AddProductTitleScreenModel(categories)))//TODO don't map to entities
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}