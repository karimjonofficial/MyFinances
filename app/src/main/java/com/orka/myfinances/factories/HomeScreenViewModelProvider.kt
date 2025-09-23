package com.orka.myfinances.factories

import com.orka.myfinances.ui.screens.home.HomeScreenViewModel

fun interface HomeScreenViewModelProvider {
    fun homeViewModel(): HomeScreenViewModel
}