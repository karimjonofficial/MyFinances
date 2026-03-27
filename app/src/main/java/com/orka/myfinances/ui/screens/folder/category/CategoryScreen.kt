package com.orka.myfinances.ui.screens.folder.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.models.ScreenTab
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
        ScreenTab(index = 0, title = stringResource(R.string.warehouse)),
        ScreenTab(index = 1, title = stringResource(R.string.products))
    )

    MultipleTabScreen(
        modifier = modifier,
        topBar = {
            CategoryScreenTopBar(
                title = if (state is State.Success) state.value.title else stringResource(R.string.loading),
                onAddProductClick = interactor::addProduct,
                onAddReceive = interactor::receive
            )
        },
        tabs = tabs,
        tabContent = tabContent
    )
}