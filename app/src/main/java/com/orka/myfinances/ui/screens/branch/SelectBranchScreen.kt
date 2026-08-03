package com.orka.myfinances.ui.screens.branch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.SearchTopAppBar
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.SelectionScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.ui.BranchUiModel

@Composable
fun SelectBranchScreen(
    modifier: Modifier = Modifier,
    state: State<Map<String, List<BranchUiModel>>>,
    interactor: SelectBranchScreenInteractor
) {
    val officeId = retain { mutableStateOf<Id?>(null) }
    val searchMode = rememberSaveable { mutableStateOf(false) }
    val searchText = rememberSaveable { mutableStateOf("") }

    SelectionScreen(
        modifier = modifier,
        topBar = {
            SearchTopAppBar(
                title = stringResource(R.string.clients),
                onSearch = interactor::search,
                searchMode = searchMode.value,
                onSearchModeChange = { searchMode.value = it },
                searchText = searchText.value,
                onSearchTextChange = { searchText.value = it }
            )
        },
        state = state,
        retry = interactor::refresh,
        bottomBar = { state ->
            SingleActionBottomBar(
                buttonText = stringResource(R.string.save),
                buttonEnabled = state !is State.Loading,
                action = {
                    val id = officeId.value
                    if (id != null) interactor.select(id)
                }
            )
        },
        isSelected = { officeId.value == it.branchId },
        onSelect = { model, _ -> officeId.value = model.branchId }
    )
}
