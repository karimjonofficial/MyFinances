package com.orka.myfinances.ui.screens.warehouse.parts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreenTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onAddProductClick: () -> Unit,
    onAddReceive: () -> Unit
) {

    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        actions = {
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