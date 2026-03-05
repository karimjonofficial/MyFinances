package com.orka.myfinances.ui.screens.host

import androidx.compose.foundation.clickable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Office
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.viewmodel.State

@Composable
fun SelectOfficeScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: SelectOfficeScreenViewModel
) {
    val office = retain { mutableStateOf<Office?>(null) }

    LazyColumnScreen(
        modifier = modifier,
        state = state,
        viewModel = viewModel,
        bottomBar = {
            SingleActionBottomBar(
                buttonText = stringResource(R.string.save),
                action = {
                    val o = office.value
                    if (o != null) viewModel.select(o)
                }
            )
        },
        item = { itemModifier, model ->
            ListItem(
                modifier = itemModifier.clickable { office.value = model.office },
                headlineContent = { Text(text = model.name) },
                leadingContent = {
                    Checkbox(
                        checked = office.value == model.office,
                        onCheckedChange = null
                    )
                }
            )
        }
    )
}