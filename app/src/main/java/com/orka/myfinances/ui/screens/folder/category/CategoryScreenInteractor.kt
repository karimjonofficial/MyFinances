package com.orka.myfinances.ui.screens.folder.category

import com.orka.myfinances.lib.ui.viewmodel.StateFul

interface CategoryScreenInteractor : StateFul {
    fun addProduct()
    fun receive()
}