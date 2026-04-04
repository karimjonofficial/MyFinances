package com.orka.myfinances.application.viewmodels.product.add

import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.toApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.data.api.scoped.office.insert
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.StateFulViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class AddProductTitleScreenViewModel(
    private val categoryId: Id,
    private val folderApi: FolderApi,
    private val productTitleApi: ProductTitleApi,
    private val flow: MutableSharedFlow<ProductTitleEvent>,
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
                val categories = folderApi.getTop()?.filterIsInstance<CategoryApiModel>()?.map { it.toItemModel() }
                if (categories != null) {
                    setState(State.Success(AddProductTitleScreenModel(categories, categoryId)))
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
        categoryId: Id
    ) {
        launch {
            val p = properties.filterNotNull()
            val oldState = state.value
            if (oldState is State.Success) {
                val category = oldState.value.categories.find { it.id == categoryId }
                if (
                    category != null && name.isNotBlank() &&
                    price != null && salePrice != null &&
                    p.size == category.template.fields.size && name.isNotEmpty() &&
                    price > 0 && salePrice > 0 && salePrice > price &&
                    category.id.value > 0
                ) {
                    val request = AddProductTitleRequest(
                        categoryId = category.id,
                        name = name,
                        price = price,
                        salePrice = salePrice,
                        properties = p,
                        description = description
                    )
                    val created = productTitleApi.insert(
                        request = request,
                        map = AddProductTitleRequest::toApiRequest
                    )
                    if (created) {
                        flow.emit(ProductTitleEvent(category.id))
                        navigator.back()
                    }
                }
            }
        }
    }

    override fun refresh() {
        launch {
            try {
                setState(State.Loading(loading))
                val categories = folderApi.getTop()?.filterIsInstance<CategoryApiModel>()?.map { it.toItemModel() }
                if (categories != null) {
                    setState(State.Success(AddProductTitleScreenModel(categories, categoryId)))
                } else setState(State.Failure(failure))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString())))
            }
        }
    }
}