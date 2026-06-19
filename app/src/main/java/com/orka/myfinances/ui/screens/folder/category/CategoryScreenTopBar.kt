package com.orka.myfinances.ui.screens.folder.category

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.lib.ui.components.SearchTopAppBar
import com.orka.myfinances.lib.ui.viewmodel.State

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreenTopBar(
    modifier: Modifier = Modifier,
    state: State<CategoryScreenModel>,
    onSearch: (String) -> Unit,
    onAddProductClick: () -> Unit,
    onAddReceive: () -> Unit,
    onExposeClick: () -> Unit,
    onUnExposeClick: () -> Unit
) {
    SearchTopAppBar(
        modifier = modifier,
        title = if (state is State.Success) state.value.title else stringResource(R.string.loading),
        onSearch = onSearch,
        actions = {
            IconButton(onClick = if(state.value?.exposed ?: false) onUnExposeClick else onExposeClick) {
                Icon(
                    painter = painterResource(if(state.value?.exposed ?: false) R.drawable.visibility_off else R.drawable.visibility),
                    contentDescription = stringResource(R.string.add)
                )
            }

            IconButton(onClick = { onAddProductClick() }) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = stringResource(R.string.add)
                )
            }

            IconButton(onClick = { onAddReceive() }) {
                Icon(
                    painter = painterResource(R.drawable.download),
                    contentDescription = stringResource(R.string.download)
                )
            }
        }
    )
}