package com.orka.myfinances.ui.screens.product.add.viewmodel

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.api.title.map
import com.orka.myfinances.data.repositories.product.title.models.AddProductTitleRequest
import com.orka.myfinances.data.repositories.product.title.models.PropertyModel
import com.orka.myfinances.lib.viewmodel.SingleStateViewModel
import com.orka.myfinances.ui.navigation.Navigator
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.asStateFlow

class AddProductTitleScreenViewModel(
    private val client: HttpClient,
    private val office: Office,
    private val navigator: Navigator,
    logger: Logger
) : SingleStateViewModel<AddProductTitleScreenState>(
    initialState = AddProductTitleScreenState.Loading,
    logger = logger
) {
    val uiState = state.asStateFlow()

    override fun initialize() {
        launch {
            try {
                val response = client.get(
                    urlString = "categories/",
                    block = { parameter(key = "branch", value = office.id.value) }
                )
                if (response.status == HttpStatusCode.OK) {
                    val categories = response.body<List<Category>>()
                    setState(AddProductTitleScreenState.Success(categories))
                } else {
                    setState(AddProductTitleScreenState.Failure)
                }
            } catch (_: Exception) {
                setState(AddProductTitleScreenState.Failure)
            }
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
                    category = category,
                    name = name,
                    price = price,
                    salePrice = salePrice,
                    properties = p,
                    description = description
                )
                try {
                    val response = client.post(
                        urlString = "product-titles/",
                        block = { setBody(request.map()) }
                    )
                    if (response.status == HttpStatusCode.Created) {
                        navigator.back()
                    }
                } catch (_: Exception) {
                    // Handle error
                }
            }
        }
    }
}
