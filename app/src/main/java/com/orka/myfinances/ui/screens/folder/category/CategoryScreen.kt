package com.orka.myfinances.ui.screens.folder.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.Tab
import com.orka.myfinances.lib.ui.screens.MultipleTabScreen
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    state: State<CategoryScreenModel>,
    interactor: CategoryScreenInteractor,
    tabContent: @Composable (index: Int) -> Unit
) {
    val tabs = listOf(
        Tab(index = 0, title = stringResource(R.string.warehouse)),
        Tab(index = 1, title = stringResource(R.string.products))
    )

    MultipleTabScreen(
        modifier = modifier,
        topBar = {
            CategoryScreenTopBar(
                state = state,
                onAddProductClick = interactor::addProduct,
                onAddReceive = interactor::receive
            )
        },
        tabs = tabs,
        tabContent = tabContent
    )
}