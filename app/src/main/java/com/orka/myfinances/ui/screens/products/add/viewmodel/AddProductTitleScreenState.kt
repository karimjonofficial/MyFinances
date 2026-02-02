package com.orka.myfinances.ui.screens.products.add.viewmodel

import com.orka.myfinances.data.models.folder.Category

interface AddProductTitleScreenState {
    data object Loading : AddProductTitleScreenState
    data object Failure : AddProductTitleScreenState
    data class Success(val categories: List<Category>) : AddProductTitleScreenState
}