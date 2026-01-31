package com.orka.myfinances.ui.screens.products.add.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.core.SingleStateViewModel
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.product.models.AddProductRequest
import com.orka.myfinances.data.repositories.product.models.PropertyModel
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.Get
import kotlinx.coroutines.flow.asStateFlow

class AddProductScreenViewModel(
    private val productRepository: Add<Product, AddProductRequest>,
    private val categoryRepository: Get<Category>,
    logger: Logger
) : SingleStateViewModel<AddProductScreenState>(
    initialState = AddProductScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            val categories = categoryRepository.get()
            if(categories != null) updateState { AddProductScreenState.Success(categories) }
            else updateState { AddProductScreenState.Failure }
        }
    }

    fun addProduct(
        titleId: Id,
        properties: List<PropertyModel<*>?>,
        name: String,
        price: Int,
        salePrice: Int,
        description: String?,
        category: Category
    ) = launch {
        val p = properties.filterNotNull()

        if (
            p.size == category.template.fields.size && name.isNotEmpty() &&
            price > 0 && salePrice > 0 && salePrice > price &&
            category.id.value > 0
        ) {
            val request = AddProductRequest(
                titleId = titleId,
                name = name,
                price = price,
                salePrice = salePrice,
                properties = p,
                description = description
            )
            productRepository.add(request)
        }
    }
}