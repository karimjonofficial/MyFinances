package com.orka.myfinances.ui.screens.folder.catalog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.folder.catalog1
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.folder.catalog.CatalogScreenInteractor
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    state: State<CatalogScreenModel>,
    dialogVisible: Boolean,
    interactor: CatalogScreenInteractor,
    onAddFolder: () -> Unit,
    dialog: @Composable () -> Unit,
    bottomSheet: @Composable () -> Unit,
    sheetVisible: Boolean
) {

    StatefulScreen(
        modifier = modifier,
        state = state,
        onRetry = interactor::refresh,
        topBar = { state ->
            TopAppBar(
                title = if (state is State.Success) state.value.name else stringResource(R.string.catalog),
                actions = {
                    IconButton(onClick = onAddFolder) {
                        Icon(
                            painter = painterResource(R.drawable.add),
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            )
        },
    ) { modifier, model ->
        CatalogContent(
            modifier = modifier,
            model = model,
            interactor = interactor
        )

        if(dialogVisible) dialog()
        if(sheetVisible) bottomSheet()
    }
}

@Preview
@Composable
private fun CatalogScreenPreview() {
    val state = State.Success(catalog1.map())
    MyFinancesTheme {
        CatalogScreen(
            modifier = Modifier.fillMaxSize(),
            state = state,
            dialogVisible = false,
            interactor = CatalogScreenInteractor.dummy,
            onAddFolder = {},
            dialog = {},
            bottomSheet = {},
            sheetVisible = false
        )
    }
}