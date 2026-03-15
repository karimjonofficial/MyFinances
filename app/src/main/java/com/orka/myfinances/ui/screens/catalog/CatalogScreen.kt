package com.orka.myfinances.ui.screens.catalog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.orka.myfinances.R
import com.orka.myfinances.fixtures.resources.models.folder.catalog1
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenInteractor
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenState
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog
import com.orka.myfinances.ui.theme.MyFinancesTheme

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    state: State,
    interactor: CatalogScreenInteractor
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    StatefulScreen<CatalogScreenModel>(
        modifier = modifier,
        state = state,
        onInitialize = interactor::initialize,
        onRetry = { interactor.initialize() },
        topBar = { state ->
            TopAppBar(
                title = if (state is State.Success<*>)
                    (state.value as CatalogScreenModel).name else stringResource(R.string.catalog),
                actions = {
                    IconButton(onClick = { dialogVisible.value = true}) {
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
            state = CatalogScreenState.Success(
                model.folders
            ),
            viewModel = interactor
        )

        if (dialogVisible.value) {
            LaunchedEffect(Unit) {
                interactor.initDialog()
            }
            val dialogState = interactor.dialogState.collectAsState()

            AddFolderDialog(
                state = dialogState.value,
                dismissRequest = { dialogVisible.value = false },
                onAddTemplateClick = {
                    interactor.navigateToAddTemplate()
                    dialogVisible.value = false
                },
                onSuccess = { name, folderType, templateId ->
                    interactor.addFolder(name, folderType, templateId)
                    dialogVisible.value = false
                },
                onCancel = { dialogVisible.value = false }
            )
        }
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
            interactor = CatalogScreenInteractor.dummy
        )
    }
}