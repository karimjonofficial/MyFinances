package com.orka.myfinances.ui.screens.stock

import com.orka.myfinances.core.Manager
import com.orka.myfinances.data.models.product.Product
import com.orka.myfinances.data.repositories.AddReceiveRequest
import com.orka.myfinances.data.repositories.ReceiveItemModel
import com.orka.myfinances.data.repositories.ReceiveMockRepository
import kotlinx.coroutines.CoroutineScope

class AddStockItemScreenViewModel(
    private val repository: ReceiveMockRepository,
    coroutineScope: CoroutineScope
) : Manager(coroutineScope) {

    fun add(product: Product, amount: Int) {
        launch {
            repository.add(
                request = AddReceiveRequest(
                    items = listOf(ReceiveItemModel(product.id.value, amount)),
                    price = product.price * amount
                )
            )
        }
    }
}