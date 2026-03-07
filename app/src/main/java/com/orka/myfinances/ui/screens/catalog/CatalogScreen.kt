package com.orka.myfinances.ui.screens.catalog

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
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenState
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenStateSuccess
import com.orka.myfinances.ui.screens.catalog.viewmodel.CatalogScreenViewModel
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: CatalogScreenViewModel
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    StatefulScreen<CatalogScreenStateSuccess>(
        modifier = modifier,
        state = state,
        onRetry = { viewModel.initialize() },
        topBar = { state ->
            TopAppBar(
                title = if (state is State.Success<*>)
                    (state.value as CatalogScreenStateSuccess).catalog.name else stringResource(R.string.catalog),
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
            viewModel = viewModel
        )

        if (dialogVisible.value) {
            LaunchedEffect(Unit) {
                viewModel.initDialog()
            }
            val dialogState = viewModel.dialogState.collectAsState()

            AddFolderDialog(
                state = dialogState.value,
                dismissRequest = { dialogVisible.value = false },
                onAddTemplateClick = {
                    viewModel.navigateToAddTemplate()
                    dialogVisible.value = false
                },
                onSuccess = { name, folderType, templateId ->
                    viewModel.addFolder(name, folderType, templateId)
                    dialogVisible.value = false
                },
                onCancel = { dialogVisible.value = false }
            )
        }
    }
}