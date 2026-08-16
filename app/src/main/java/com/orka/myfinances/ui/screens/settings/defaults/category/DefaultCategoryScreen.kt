package com.orka.myfinances.ui.screens.settings.defaults.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.SelectionScreen
import com.orka.myfinances.lib.ui.state.State
import com.orka.myfinances.ui.models.item.CategoryItemModel

@Composable
fun DefaultCategoryScreen(
    modifier: Modifier = Modifier,
    state: State<Map<String, List<CategoryItemModel>>>,
    selectedState: State<Id?>,
    interactor: SelectDefaultCategoryInteractor,
    refresh: () -> Unit
) {
    val localSelectedId = rememberSaveable { mutableStateOf(selectedState.value?.value) }

    LaunchedEffect(selectedState) {
        if (selectedState is State.Success) {
            localSelectedId.value = selectedState.value?.value
        }
    }

    SelectionScreen(
        modifier = modifier,
        title = stringResource(R.string.default_category),
        state = state,
        bottomBar = { listState ->
            val selectedId = localSelectedId.value
            SingleActionBottomBar(
                buttonText = stringResource(R.string.save),
                buttonEnabled = listState is State.Success && selectedId != null && selectedId != selectedState.value?.value,
                action = { selectedId?.let { interactor.select(Id(it)) } }
            )
        },
        isSelected = { it.id.value == localSelectedId.value },
        onSelect = { item, _ ->
            localSelectedId.value = item.id.value
        },
        retry = refresh
    )
}
