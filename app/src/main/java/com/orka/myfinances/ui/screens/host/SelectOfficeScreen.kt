package com.orka.myfinances.ui.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.LazyColumnScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.host.components.OfficeCard
import com.orka.myfinances.ui.screens.host.components.OfficeUiModel

@Composable
fun SelectOfficeScreen(
    modifier: Modifier = Modifier,
    state: State<List<OfficeUiModel>>,
    interactor: SelectOfficeScreenInteractor
) {
    val officeId = retain { mutableStateOf<Id?>(null) }

    LazyColumnScreen(
        modifier = modifier,
        title = stringResource(R.string.select_your_office),
        state = state,
        refresh = interactor::refresh,
        bottomBar = {
            SingleActionBottomBar(
                buttonText = stringResource(R.string.save),
                action = {
                    val id = officeId.value
                    if (id != null) interactor.select(id)
                }
            )
        },
        item = { model ->
            OfficeCard(
                model = model,
                checked = officeId.value == model.officeId,
                onChecked = { officeId.value = it.officeId }
            )
        }
    )
}