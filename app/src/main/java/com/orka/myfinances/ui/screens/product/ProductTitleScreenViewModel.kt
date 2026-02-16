package com.orka.myfinances.ui.screens.product

import com.orka.myfinances.lib.ui.viewmodel.Manager
import com.orka.myfinances.data.models.product.ProductTitle
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.ReceiveItemModel
import com.orka.myfinances.lib.data.repositories.Add

class ProductTitleScreenViewModel(
    private val productTitle: ProductTitle,
    private val repository: Add<Receive, AddReceiveRequest>
) : Manager() {
    fun receive(price: Int, salePrice: Int, amount: Int, totalPrice: Int, comment: String?) {
        launch {
            val request = AddReceiveRequest(
                items = listOf(ReceiveItemModel(
                    productTitleId = productTitle.id,
                    price = price,
                    salePrice = salePrice,
                    amount = amount
                )),
                price = totalPrice,
                comment = comment
            )
            repository.add(request)
        }
    }
}