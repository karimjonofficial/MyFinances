package com.orka.myfinances.ui.screens.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.SearchTopAppBar
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.LazyColumnWithStickyHeaderScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.screens.host.components.BranchCard
import com.orka.myfinances.ui.screens.host.components.BranchUiModel

@Composable
fun SelectBranchScreen(
    modifier: Modifier = Modifier,
    state: State<Map<String, List<BranchUiModel>>>,
    interactor: SelectBranchScreenInteractor
) {
    val officeId = retain { mutableStateOf<Id?>(null) }

    LazyColumnWithStickyHeaderScreen(
        modifier = modifier,
        topBar = {
            SearchTopAppBar(
                title = stringResource(R.string.select_your_branch),
                onSearch = interactor::search
            )
        },
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
        arrangementSpace = 4.dp,
        item = { model ->
            BranchCard(
                model = model,
                checked = officeId.value == model.branchId,
                onChecked = { officeId.value = it.branchId }
            )
        }
    )
}
