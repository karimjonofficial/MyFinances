package com.orka.myfinances.application.viewmodels.product.edit

import com.orka.myfinances.application.viewmodels.product.add.toItemModel
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.models.response.CategoryApiModel
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.toApiRequest
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.repositories.product.title.ProductTitleEvent
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.data.repositories.product.title.models.UpdateProductTitleRequest
import com.orka.myfinances.lib.data.api.getById
import com.orka.myfinances.lib.data.api.scoped.office.update
import com.orka.myfinances.lib.logger.Logger
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.lib.viewmodel.BaseViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.screens.product.edit.EditProductTitleScreenInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asStateFlow

class EditProductTitleScreenViewModel(
    private val productId: Id,
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
        val productTitle = productTitleApi.getById(productId)

        if (categories != null && productTitle != null) {
            flow.emit(ProductTitleEvent(Id(productTitle.category), productId))
            State.Success(productTitle.toEditorModel(categories))
        } else null
    },
    logger = logger
), EditProductTitleScreenInteractor {
    val uiState = state.asStateFlow()

    init {
        initialize()
    }

    override fun updateProductTitle(
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int?,
        salePrice: Int?,
        description: String?,
        categoryId: Id
    ) {
        launch {
            val oldState = state.value
            try {
                if (oldState is State.Success) {
                    val selectedCategory = oldState.value.categories.find { it.id == categoryId }
                    val validProperties = properties.filterNotNull()

                    if (
                        selectedCategory != null && name.isNotBlank() &&
                        price != null && salePrice != null &&
                        validProperties.size == selectedCategory.template.fields.size &&
                        price > 0 && salePrice > 0 && salePrice > price &&
                        categoryId.value > 0
                    ) {
                        setState(State.Loading(loading, oldState.value))
                        val request = UpdateProductTitleRequest(
                            categoryId = categoryId,
                            name = name,
                            price = price,
                            salePrice = salePrice,
                            properties = validProperties,
                            description = description
                        )
                        val updated = productTitleApi.update(
                            id = productId,
                            request = request,
                            map = UpdateProductTitleRequest::toApiRequest
                        )

                        if (updated) {
                            flow.emit(ProductTitleEvent(selectedCategory.id, productId))
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
