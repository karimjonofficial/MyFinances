package com.orka.myfinances.ui.screens.host

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
import com.orka.myfinances.ui.screens.host.components.OfficeCard
import com.orka.myfinances.application.viewmodels.office.SelectOfficeScreenViewModel

@Composable
fun SelectOfficeScreen(
    modifier: Modifier = Modifier,
    state: State,
    viewModel: SelectOfficeScreenViewModel
) {
    val office = retain { mutableStateOf<Office?>(null) }

    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.select_your_office),
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
        item = { modifier, model ->
            OfficeCard(
                modifier = modifier,
                model = model,
                checked = office.value == model.office,
                onChecked = { office.value = it.office }
            )
        }
    )
}

