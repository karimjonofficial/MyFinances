package com.orka.myfinances.ui.screens.home

import com.orka.myfinances.models.Category

sealed interface HomeScreenState {
    data object Initial : HomeScreenState
    data object Loading : HomeScreenState
    data class Success(val data: List<Category>) : HomeScreenState
    data class Error(val message: String) : HomeScreenState
}
