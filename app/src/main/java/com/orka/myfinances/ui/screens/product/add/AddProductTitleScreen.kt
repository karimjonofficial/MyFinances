package com.orka.myfinances.ui.screens.product.add

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.fixtures.resources.models.folder.categories
import com.orka.myfinances.fixtures.resources.models.folder.category1
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenInteractor
import com.orka.myfinances.ui.screens.product.add.interactor.AddProductTitleScreenModel
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun AddProductTitleScreen(
    modifier: Modifier = Modifier,
    state: State<AddProductTitleScreenModel>,
    interactor: AddProductTitleScreenInteractor
) {
    ProductTitleEditorScreen(
        modifier = modifier,
        title = stringResource(R.string.add_product),
        state = state,
        onRetry = interactor::refresh,
        onSave = interactor::addProductTitle
    )
}

@Preview
@Composable
private fun AddProductTitleScreenPreview() {
    MyFinancesTheme {
        AddProductTitleScreen(
            modifier = Modifier.fillMaxSize(),
            state = State.Success(AddProductTitleScreenModel(categories.map { it.toItemModel() }, category1.id)),
            interactor = AddProductTitleScreenInteractor.dummy
        )
    }
}
