package com.orka.myfinances.lib.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.orka.myfinances.data.models.Branch

@Composable
inline fun <reified T: ViewModel> viewModel(
    branch: Branch,
    key: String? = null,
    noinline initializer: CreationExtras.() -> T
): T {
    return viewModel(
        key = if(key != null) "${branch.id} $key" else "${branch.id}",
        initializer = initializer
    )
}