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

class AddReceiveScreenViewModel(
    private val receiveRepository: Add<Receive, AddReceiveRequest>,
    category: Category,
    titleRepository: GetByParameter<ProductTitle, Category>,
    loading: UiText,
    failure: UiText,
    logger: Logger
) : ListByParameterViewModel<ProductTitle, Category>(
    parameter = category,
    loading = loading,
    failure = failure,
    repository = titleRepository,
    logger = logger
) {

    fun add(
        title: ProductTitle,
        amount: Int,
        price: Int,
        salePrice: Int,
        totalPrice: Int,
        description: String?
    ) {
        launch {
            receiveRepository.add(AddReceiveRequest(
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
                ))
        }
    }
}