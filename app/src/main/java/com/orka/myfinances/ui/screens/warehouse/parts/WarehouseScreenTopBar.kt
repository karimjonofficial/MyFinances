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
import com.orka.myfinances.data.models.folder.Warehouse

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreenTopBar(
    modifier: Modifier = Modifier,
    warehouse: Warehouse,
    onAddProductClick: (Warehouse) -> Unit,
    onAddStockItemClick: (Warehouse) -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = warehouse.name) },
        actions = {
            IconButton(onClick = { onAddProductClick(warehouse) }) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = stringResource(R.string.add)
                )
            }

            IconButton(onClick = { onAddStockItemClick(warehouse) }) {
                Icon(
                    painter = painterResource(R.drawable.download),
                    contentDescription = stringResource(R.string.download)
                )
            }
        }
    )
}