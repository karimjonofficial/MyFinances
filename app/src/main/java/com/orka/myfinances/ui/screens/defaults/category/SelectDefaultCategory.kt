package com.orka.myfinances.ui.screens.defaults.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orka.myfinances.R
import com.orka.myfinances.data.models.Id
import com.orka.myfinances.lib.ui.components.LazyColumnWithStickHeader
import com.orka.myfinances.lib.ui.components.SingleActionBottomBar
import com.orka.myfinances.lib.ui.screens.StatefulScreen
import com.orka.myfinances.lib.ui.viewmodel.State
import com.orka.myfinances.ui.components.items.DefaultCategoryItem

@Composable
fun SelectDefaultCategory(
    modifier: Modifier = Modifier,
    state: State<SelectDefaultCategoryScreenModel>,
    interactor: SelectDefaultCategoryInteractor
) {
    val localSelectedId = rememberSaveable { mutableStateOf(state.value?.defaultId?.value) }

    LaunchedEffect(state.value?.defaultId) {
        state.value?.defaultId?.let {
            localSelectedId.value = it.value
        }
    }

    StatefulScreen(
        modifier = modifier,
        state = state,
        onRetry = interactor::refresh,
        bottomBar = {
            val model = state.value
            if (model != null) {
                SingleActionBottomBar(
                    buttonText = stringResource(R.string.save),
                    buttonEnabled = localSelectedId.value != null && localSelectedId.value != model.defaultId?.value,
                    action = { localSelectedId.value?.let { interactor.select(Id(it)) } }
                )
            }
        }
    ) { modifier, model ->
        LazyColumnWithStickHeader(
            modifier = modifier,
            map = model.map,
            item = { item ->
                DefaultCategoryItem(
                    model = item,
                    selected = localSelectedId.value == item.id.value,
                    onClick = { localSelectedId.value = item.id.value }
                )
            }
        )
    }
}