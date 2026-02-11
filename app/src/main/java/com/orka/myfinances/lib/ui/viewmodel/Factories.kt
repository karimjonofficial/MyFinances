package com.orka.myfinances.lib.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.data.models.Office

@Composable
inline fun <reified T: ViewModel> viewModel(
    office: Office,
    key: String? = null,
    noinline initializer: CreationExtras.() -> T
): T {
    return viewModel(
        key = if(key != null) "${office.id} $key" else "${office.id}",
        initializer = initializer
    )
}