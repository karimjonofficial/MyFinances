package com.orka.myfinances.ui.screens.receive.add

import com.orka.myfinances.core.Logger
import com.orka.myfinances.data.models.folder.Category
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.ReceiveItemModel
import com.orka.myfinances.lib.data.repositories.Add
import com.orka.myfinances.lib.data.repositories.GetByParameter
import com.orka.myfinances.lib.ui.models.UiText
import com.orka.myfinances.lib.ui.viewmodel.ListByParameterViewModel
import com.orka.myfinances.ui.navigation.Navigator

class AddReceiveScreenViewModel(
    category: Category,
    private val add: Add<Receive, AddReceiveRequest>,
    get: GetByParameter<ProductTitle, Category>,
    loading: UiText,
    failure: UiText,
    private val navigator: Navigator,
    logger: Logger
) : ListByParameterViewModel<ProductTitle, Category>(
    parameter = category,
    loading = loading,
    failure = failure,
    repository = get,
    logger = logger
) {

    fun add(
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
                        ReceiveItemModel(
                            productTitleId = title.id,
                            amount = amount,
                            price = price,
                            salePrice = salePrice,
                            description = description
                        )
                    ),
                    price = totalPrice
                )
                add.add(request)
                navigator.back()
            }
        }
    }
}