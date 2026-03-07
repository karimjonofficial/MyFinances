package com.orka.myfinances.ui.screens.warehouse.viewmodel

import com.orka.myfinances.data.models.Id
import com.orka.myfinances.data.models.folder.Category

interface WarehouseInteractor {
    fun addToBasket(id: Id)
    fun addProduct(category: Category)
    fun receive(category: Category)
    fun selectProduct(id: Id)
    fun initialize()
}