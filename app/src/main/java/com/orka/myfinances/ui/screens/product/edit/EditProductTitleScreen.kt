package com.orka.myfinances.ui.screens.product.edit

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.screens.product.add.ProductTitleEditorScreen
import com.orka.myfinances.ui.models.screen.AddProductTitleScreenModel

@Composable
fun EditProductTitleScreen(
    modifier: Modifier = Modifier,
    state: State<AddProductTitleScreenModel>,
    interactor: EditProductTitleScreenInteractor
) {
    ProductTitleEditorScreen(
        modifier = modifier,
        title = stringResource(R.string.edit),
        state = state,
        onRetry = interactor::refresh,
        onSave = interactor::updateProductTitle
    )
}
