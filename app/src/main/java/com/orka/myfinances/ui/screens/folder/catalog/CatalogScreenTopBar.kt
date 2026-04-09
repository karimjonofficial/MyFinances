package com.orka.myfinances.ui.screens.folder.catalog

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.TopAppBar
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun CatalogScreenTopBar(
    modifier: Modifier = Modifier,
    state: State<CatalogScreenModel>,
    onAddFolder: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
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
}