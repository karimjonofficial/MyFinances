package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.Id

interface WarehouseScreenInteractor {
    fun addToBasket(id: Id)
    fun addProduct()
    fun receive()
    fun selectProduct(id: Id)
    fun initialize()

    companion object {
        val dummy = object : WarehouseScreenInteractor {
            override fun addToBasket(id: Id) {}
            override fun addProduct() {}
            override fun receive() {}
            override fun selectProduct(id: Id) {}
            override fun initialize() {}
        }
    }
}