package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.core.Manager
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.models.receive.Receive
import com.orka.myfinances.data.repositories.receive.AddReceiveRequest
import com.orka.myfinances.data.repositories.receive.ReceiveItemModel
import com.orka.myfinances.lib.data.repositories.Add

class AddReceiveScreenViewModel(
    private val repository: Add<Receive, AddReceiveRequest>
) : Manager() {

    fun add(product: Product, amount: Int) {
        launch {
            repository.add(
                request = AddReceiveRequest(
                    items = listOf(ReceiveItemModel(product.id.value, amount)),
                    price = product.title.defaultPrice * amount
                )
            )
        }
    }
}