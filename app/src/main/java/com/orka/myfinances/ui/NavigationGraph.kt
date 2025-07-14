package com.orka.myfinances.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.orka.myfinances.ui.screens.home.HomeScreen
import com.orka.myfinances.ui.screens.home.HomeScreenViewModel
import com.orka.myfinances.ui.screens.home.parts.AddProductDialog

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    dialogVisible: MutableState<Boolean>,
    viewModel: HomeScreenViewModel
) {
    val uiState = viewModel.uiState.collectAsState()

    HomeScreen(
        modifier = modifier,
        state = uiState.value
    )

    if (dialogVisible.value) {
        AddProductDialog(
            dismissRequest = { dialogVisible.value = false },
            onSuccess = {
                viewModel.addCategory(it)
                dialogVisible.value = false
            },
            onCancel = { dialogVisible.value = false }
        )
    }
}