package com.orka.myfinances.ui.screens.product.add.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import kotlinx.coroutines.flow.asStateFlow

class AddProductTitleScreenViewModel(
    private val productTitleRepository: Add<ProductTitle, AddProductTitleRequest>,
    private val categoryRepository: Get<Category>,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<AddProductTitleScreenState>(
    initialState = AddProductTitleScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val categories = categoryRepository.get()
            if(categories != null) updateState { AddProductTitleScreenState.Success(categories) }
            else updateState { AddProductTitleScreenState.Failure }
        }
    }

    fun addProductTitle(
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
                    categoryId = category.id,
                    name = name,
                    price = price,
                    salePrice = salePrice,
                    properties = p,
                    description = description
                )
                productTitleRepository.add(request)
                navigator.back()
            }
        }
    }
}