package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category

interface WarehouseScreenInteractor {
    fun addToBasket(id: Id)
    fun addProduct(category: Category)
    fun receive(category: Category)
    fun selectProduct(id: Id)
    fun initialize()

    companion object {
        val dummy = object : WarehouseScreenInteractor {
            override fun addToBasket(id: Id) {}
            override fun addProduct(category: Category) {}
            override fun receive(category: Category) {}
            override fun selectProduct(id: Id) {}
            override fun initialize() {}
        }
    }
}