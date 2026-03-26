package com.orka.myfinances.ui.screens.folder.category

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    state: State<CategoryScreenModel>,
    interactor: CategoryScreenInteractor,
    content: @Composable (PaddingValues, State<CategoryScreenModel>) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CategoryScreenTopBar(
                title = state.value?.title ?: stringResource(R.string.loading),
                onAddProductClick = interactor::addProduct,
                onAddReceive = interactor::receive
            )
        }
    ) { paddingValues ->
        content(paddingValues, state)
    }
}