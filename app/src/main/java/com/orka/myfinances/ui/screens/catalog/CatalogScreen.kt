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
import com.orka.myfinances.data.models.folder.Catalog
import com.orka.myfinances.lib.extensions.ui.scaffoldPadding
import com.orka.myfinances.lib.ui.Scaffold
import com.orka.myfinances.ui.managers.Navigator
import com.orka.myfinances.ui.screens.home.parts.AddFolderDialog

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    catalog: Catalog,
    state: CatalogScreenState,
    viewModel: CatalogScreenViewModel,
    navigator: Navigator
) {
    val dialogVisible = rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        title = catalog.name,
        actions = {
            IconButton(onClick = { dialogVisible.value = true }) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) { paddingValues ->
        CatalogContent(
            modifier = Modifier.scaffoldPadding(paddingValues),
            state = state,
            viewModel = viewModel,
            navigator = navigator
        )

        if (dialogVisible.value) {
            LaunchedEffect(Unit) {
                viewModel.initDialog()
            }
            val dialogState = viewModel.dialogState.collectAsState()

            AddFolderDialog(
                state = dialogState.value,
                dismissRequest = { dialogVisible.value = false },
                onAddTemplateClick = { navigator.navigateToAddTemplate() },
                onSuccess = { name, folderType, templateId ->
                    viewModel.addFolder(name, folderType, templateId)
                    dialogVisible.value = false
                },
                onCancel = { dialogVisible.value = false }
            )
        }
    }
}