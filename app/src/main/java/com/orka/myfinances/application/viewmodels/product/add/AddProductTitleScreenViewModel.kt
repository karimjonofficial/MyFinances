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
import com.orka.myfinances.lib.viewmodel.BaseViewModel
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
    loading: UiText,
    failure: UiText,
    logger: Logger
) : BaseViewModel<AddProductTitleScreenModel>(
    loading = loading,
    failure = failure,
    produceSuccess = {
        val categories = folderApi.getTop()?.filterIsInstance<CategoryApiModel>()?.map { it.toItemModel() }
        if (categories != null) {
            State.Success(AddProductTitleScreenModel(categories, categoryId))
        } else null
    },
    logger = logger
), AddProductTitleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun addProductTitle(
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int?,
        salePrice: Int?,
        exposedPrice: Int?,
        description: String?,
        categoryId: Id
    ) {
        launch {
            val oldState = state.value
            try {
                if (oldState is State.Success) {
                    val p = properties.filterNotNull()
                    val category = oldState.value.categories.find { it.id == categoryId }
                    if (
                        category != null && name.isNotBlank() &&
                        price != null && salePrice != null && exposedPrice != null &&
                        p.size == category.template.fields.size && name.isNotEmpty() &&
                        price > 0 && salePrice > 0 && salePrice > price &&
                        category.id.value > 0
                    ) {
                        setState(State.Loading(loading, oldState.value))
                        val request = AddProductTitleRequest(
                            categoryId = category.id,
                            name = name,
                            price = price,
                            salePrice = salePrice,
                            exposedPrice = exposedPrice,
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
                        } else setState(State.Failure(failure, oldState.value))
                    }
                } else setState(State.Failure(UiText.Str("Action executed from wrong state"), oldState.value))
            } catch (e: Exception) {
                setState(State.Failure(UiText.Str(e.message.toString()), oldState.value))
            }
        }
    }
}