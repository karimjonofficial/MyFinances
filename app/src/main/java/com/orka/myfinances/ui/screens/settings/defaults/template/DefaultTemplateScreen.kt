package com.orka.myfinances.ui.screens.settings.defaults.template

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.models.ChunkUiModel
import com.orka.myfinances.lib.ui.screens.PaginatedSelectionScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.item.TemplateItemModel

@Composable
fun DefaultTemplateScreen(
    modifier: Modifier = Modifier,
    state: State<ChunkUiModel<TemplateItemModel>>,
    selectedState: State<Id?>,
    interactor: SelectDefaultTemplateInteractor,
    loadMore: () -> Unit,
    refresh: () -> Unit
) {
    val localSelectedId = rememberSaveable { mutableStateOf(selectedState.value?.value) }

    LaunchedEffect(selectedState) {
        if (selectedState is State.Success) {
            localSelectedId.value = selectedState.value?.value
        }
    }

    PaginatedSelectionScreen(
        modifier = modifier,
        title = stringResource(R.string.default_template),
        state = state,
        bottomBar = { listState ->
            val selectedId = localSelectedId.value
            SingleActionBottomBar(
                buttonText = stringResource(R.string.save),
                buttonEnabled = listState.value != null && selectedId != null && selectedId != selectedState.value?.value,
                action = { selectedId?.let { interactor.select(Id(it)) } }
            )
        },
        isSelected = { it.id.value == localSelectedId.value },
        onSelect = { item, _ ->
            localSelectedId.value = item.id.value
        },
        loadMore = loadMore,
        refresh = refresh
    )
}
