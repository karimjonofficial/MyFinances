package com.orka.myfinances.ui.screens.folder.category

import com.orka.myfinances.lib.ui.viewmodel.Refreshable

interface CategoryScreenInteractor : Refreshable {
    fun addProduct()
    fun receive()
}
