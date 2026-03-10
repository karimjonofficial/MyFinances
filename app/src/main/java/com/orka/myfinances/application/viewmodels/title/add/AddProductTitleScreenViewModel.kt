package com.orka.myfinances.application.viewmodels.title.add

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.api.folder.FolderApi
import com.orka.myfinances.data.api.folder.map
import com.orka.myfinances.data.api.title.ProductTitleApi
import com.orka.myfinances.data.api.title.map
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenState
import kotlinx.coroutines.flow.asStateFlow

class AddProductTitleScreenViewModel(
    private val folderApi: FolderApi,
    private val productTitleApi: ProductTitleApi,
    private val office: Office,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<AddProductTitleScreenState>(
    initialState = AddProductTitleScreenState.Loading,
    logger = logger
), AddProductTitleScreenInteractor {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val categories = folderApi.getByOffice(office.id.value)
                    ?.map { it.map() }
                    ?.filterIsInstance<Category>()
                if (categories != null) {
                    setState(AddProductTitleScreenState.Success(categories))
                } else {
                    setState(AddProductTitleScreenState.Failure)
                }
            } catch (_: Exception) {
                setState(AddProductTitleScreenState.Failure)
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
}